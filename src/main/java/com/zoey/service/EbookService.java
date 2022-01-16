package com.zoey.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zoey.domain.Ebook;
import com.zoey.domain.EbookExample;
import com.zoey.mapper.EbookMapper;
import com.zoey.reps.EbookResp;
import com.zoey.reps.PageResp;
import com.zoey.req.EbookReq;
import com.zoey.util.CopyUtil;
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

//    public List<Ebook> getList() {
//        return ebookMapper.selectByExample(null);
//        //return ebookMapper.selectByPrimaryKey();
//    }

    public PageResp<EbookResp> getList(EbookReq ebookReq) {

        EbookExample example = new EbookExample();
        EbookExample.Criteria criteria = example.createCriteria();
        // 增加动态SQL
        if (!ObjectUtils.isEmpty(ebookReq.getName())) {
            criteria.andNameLike("%" + ebookReq.getName() + "%");
        }

        // 后端分页，写在要分页的查询前面，因为它只对一个sql查询生效
        PageHelper.startPage(ebookReq.getPage(),ebookReq.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(example);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        logger.info("Total rows: {}", pageInfo.getTotal());
        logger.info("Total pages: {}", pageInfo.getPages());
        logger.info("Page num: {}", pageInfo.getPageNum());

        // List<EbookResp> ebookResps = new ArrayList<>();
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
////            ebookResp.setId(1234L);
//            ebookResps.add(ebookResp);
//        }
//        EbookResp ebookResp = new EbookResp();
//        Page page = new Page(pageInfo.getTotal(),pageInfo.getPageNum());
//        ebookResp.setPageInfo(page);
        PageResp<EbookResp> pageResp = new PageResp<>();
        pageResp.setList(list);
        pageResp.setTotalNum(pageInfo.getTotal());

        return pageResp;
    }

    public Ebook getEbookById(long id) {
        return ebookMapper.selectByPrimaryKey(id);
    }
}
