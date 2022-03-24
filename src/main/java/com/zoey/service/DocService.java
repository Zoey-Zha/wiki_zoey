package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.Doc;
import com.zoey.domain.DocExample;
import com.zoey.mapper.DocMapper;
import com.zoey.reps.DocQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.DocQueryReq;
import com.zoey.req.DocSaveReq;
import com.zoey.util.CopyUtil;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class DocService {

    // private static final Logger logger = LoggerFactory.getLogger(DocService.class);
    private static final Logger logger = LoggerFactory.getLogger(DocService.class);

    @Autowired
    private DocMapper docMapper;

    @Autowired
    private SnowFlake snowFlake;

//    public List<Doc> getList() {
//        return docMapper.selectByExample(null);
//        //return docMapper.selectByPrimaryKey();
//    }

    public PageResp<DocQueryResp> getList(DocQueryReq docQueryReq) {

        DocExample example = new DocExample();
        DocExample.Criteria criteria = example.createCriteria();
        // 增加动态SQL
//        if (!ObjectUtils.isEmpty(docQueryReq.getName())) {
//            criteria.andNameLike("%" + docQueryReq.getName() + "%");
//        }

        // 后端分页，写在要分页的查询前面，因为它只对一个sql查询生效
        PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        List<Doc> docList = docMapper.selectByExample(example);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        logger.info("Total rows: {}", pageInfo.getTotal());
        logger.info("Total pages: {}", pageInfo.getPages());
        logger.info("Page num: {}", pageInfo.getPageNum());
        logger.info("The whole list" + docList);

        // List<DocResp> docResps = new ArrayList<>();
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
//        for (Doc doc : docList) {
//            DocResp docResp = new DocResp();
//            BeanUtils.copyProperties(doc,docResp);
////            docResp.setId(1234L);
//            docResps.add(docResp);
//        }
//        DocResp docResp = new DocResp();
//        Page page = new Page(pageInfo.getTotal(),pageInfo.getPageNum());
//        docResp.setPageInfo(page);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotalNum(pageInfo.getTotal());

        return pageResp;
    }

    /**
     * Get all data without pages
     * @return
     */
    public List<DocQueryResp> all() {

        DocExample example = new DocExample();
        example.setOrderByClause("sort asc");
        // 增加动态SQL
//        if (!ObjectUtils.isEmpty(docQueryReq.getName())) {
//            criteria.andNameLike("%" + docQueryReq.getName() + "%");
//        }

        // 后端分页，写在要分页的查询前面，因为它只对一个sql查询生效
        // PageHelper.startPage(docQueryReq.getPage(), docQueryReq.getSize());
        List<Doc> docList = docMapper.selectByExample(example);

        // List<DocResp> docResps = new ArrayList<>();
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);
//        for (Doc doc : docList) {
//            DocResp docResp = new DocResp();
//            BeanUtils.copyProperties(doc,docResp);
////            docResp.setId(1234L);
//            docResps.add(docResp);
//        }
//        DocResp docResp = new DocResp();
//        Page page = new Page(pageInfo.getTotal(),pageInfo.getPageNum());
//        docResp.setPageInfo(page);


        return list;
    }

    public void save(DocSaveReq req){
        // 如何把DocSaveReq转换成Doc, 我一下子没有到这种方法，只想到必须转换
        Doc doc = CopyUtil.copy(req,Doc.class);
        System.out.println(doc.toString());

        // 复用save实现新增新增
        if (ObjectUtils.isEmpty(req.getId())){
            // insert
            //  = new SnowFlake(1, 1);
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);

        } else {
            // update
            docMapper.updateByPrimaryKey(doc);
        }

    }

    public void delete(long id){
        docMapper.deleteByPrimaryKey(id);
    }

    public Doc getDocById(long id) {
        return docMapper.selectByPrimaryKey(id);
    }
}
