package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.Content;
import com.zoey.domain.Doc;
import com.zoey.domain.DocExample;
import com.zoey.exception.BusinessException;
import com.zoey.exception.BusinessExceptionCode;
import com.zoey.mapper.ContentMapper;
import com.zoey.mapper.DocMapper;
import com.zoey.mapper.DocMapperCust;
import com.zoey.reps.DocQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.DocQueryReq;
import com.zoey.req.DocSaveReq;
import com.zoey.util.CopyUtil;
import com.zoey.util.RedisUtil;
import com.zoey.util.RequestContext;
import com.zoey.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

import static java.lang.Long.parseLong;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private ContentMapper contentMapper;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    public RedisUtil redisUtil;

    @Resource
    public WebSocketService webSocketService;


//     @Resource
//     private RocketMQTemplate rocketMQTemplate;


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
        LOG.info("Total rows: {}", pageInfo.getTotal());
        LOG.info("Total pages: {}", pageInfo.getPages());
        LOG.info("Page num: {}", pageInfo.getPageNum());
        LOG.info("The whole list" + docList);

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

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<DocResp> respList = new ArrayList<>();
        // for (Doc doc : docList) {
        //     // DocResp docResp = new DocResp();
        //     // BeanUtils.copyProperties(doc, docResp);
        //     // 对象复制
        //     DocResp docResp = CopyUtil.copy(doc, DocResp.class);
        //
        //     respList.add(docResp);
        // }

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp();
        // pageResp.setTotal(pageInfo.getTotal());
        pageResp.setTotalNum(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    public List<DocQueryResp> all(String ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(parseLong(ebookId));
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        return list;
    }

    /**
     * Get all data without pages
     * @return
     */
    public List<DocQueryResp> allFromMe() {

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

    /*
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
    */

    public PageResp<DocQueryResp> listOld(DocQueryReq req) {
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        // List<DocResp> respList = new ArrayList<>();
        // for (Doc doc : docList) {
        //     // DocResp docResp = new DocResp();
        //     // BeanUtils.copyProperties(doc, docResp);
        //     // 对象复制
        //     DocResp docResp = CopyUtil.copy(doc, DocResp.class);
        //
        //     respList.add(docResp);
        // }

        // 列表复制
        List<DocQueryResp> list = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp();
        // 由setTotal 改为了setTotalNum(),不知道是否由影响
        pageResp.setTotalNum(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 保存
     */
     @Transactional
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        // LOG.info("content的内容是空吗：", content.getContent()); // yes
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }

    /**
     *
     * @param id
     * @return
     */
    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        // 文档阅读数+1
        docMapperCust.increaseViewCount(id);
        if (ObjectUtils.isEmpty(content)) {
            return "";
        } else {
            return content.getContent();
        }
    }

    /**
     * 点赞
     */
    public void vote(Long id) {
         docMapperCust.increaseVoteCount(id);
        // 远程IP+doc.id作为key，24小时内不能重复
        String ip = RequestContext.getRemoteAddr();
        if (redisUtil.validateRepeat("DOC_VOTE_" + id + "_" + ip, 5000)) {
            docMapperCust.increaseVoteCount(id);
        } else {
            throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
        }

        // 推送消息
        Doc docDb = docMapper.selectByPrimaryKey(id);
//        String logId = MDC.get("LOG_ID");
//        wsService.sendInfo("【" + docDb.getName() + "】被点赞！", logId);
//        rocketMQTemplate.convertAndSend("VOTE_TOPIC", "【" + docDb.getName() + "】被点赞！");

        // 尝试解藕，异步化，使用新的线程Thread
//        webSocketServer.sendInfo("【" + docDb.getName() + "】Liked!");
        // 单一设计原则，docDb还是在这里获取
        String LOG_ID = MDC.get("LOG_ID");
        webSocketService.sendInfo("【" + docDb.getName() + "】Liked!", LOG_ID);
//        rocketMQTemplate.convertAndSend("tinterTopic", "【" + docDb.getName() + "】Liked!");
    }

    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }
}
