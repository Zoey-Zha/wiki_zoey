package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.Ebook;
import com.zoey.domain.EbookExample;
import com.zoey.mapper.EbookMapper;
import com.zoey.reps.EbookQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.EbookQueryReq;
import com.zoey.req.EbookSaveReq;
import com.zoey.util.CopyUtil;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EbookService {

    // private static final Logger logger = LoggerFactory.getLogger(EbookService.class);
    private static final Logger logger = LoggerFactory.getLogger(EbookService.class);

    @Autowired
    private EbookMapper ebookMapper;

    @Autowired
    private SnowFlake snowFlake;

//    public List<Ebook> getList() {
//        return ebookMapper.selectByExample(null);
//        //return ebookMapper.selectByPrimaryKey();
//    }

    public PageResp<EbookQueryResp> getList(EbookQueryReq ebookQueryReq) {

        EbookExample example = new EbookExample();
        EbookExample.Criteria criteria = example.createCriteria();
        // 增加动态SQL
        if (!ObjectUtils.isEmpty(ebookQueryReq.getName())) {
            criteria.andNameLike("%" + ebookQueryReq.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(ebookQueryReq.getCategoryId2())) {
            criteria.andCategory2IdEqualTo(ebookQueryReq.getCategoryId2());
        }

        // 后端分页，写在要分页的查询前面，因为它只对一个sql查询生效
        PageHelper.startPage(ebookQueryReq.getPage(), ebookQueryReq.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(example);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        logger.info("Total rows: {}", pageInfo.getTotal());
        logger.info("Total pages: {}", pageInfo.getPages());
        logger.info("Page num: {}", pageInfo.getPageNum());
        logger.info("The whole list" + ebookList);

        // List<EbookResp> ebookResps = new ArrayList<>();
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
////            ebookResp.setId(1234L);
//            ebookResps.add(ebookResp);
//        }
//        EbookResp ebookResp = new EbookResp();
//        Page page = new Page(pageInfo.getTotal(),pageInfo.getPageNum());
//        ebookResp.setPageInfo(page);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotalNum(pageInfo.getTotal());

        return pageResp;
    }

    public void save(EbookSaveReq req){
        // 如何把EbookSaveReq转换成Ebook, 我一下子没有到这种方法，只想到必须转换
        Ebook ebook = CopyUtil.copy(req,Ebook.class);
        System.out.println(ebook.toString());

        // 复用save实现新增新增
        if (ObjectUtils.isEmpty(req.getId())){
            // insert
            //  = new SnowFlake(1, 1);
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);

        } else {
            // update
            ebookMapper.updateByPrimaryKey(ebook);
        }

    }

    public void delete(long id){
        ebookMapper.deleteByPrimaryKey(id);
    }

    public Ebook getEbookById(long id) {
        return ebookMapper.selectByPrimaryKey(id);
    }
}
