package com.zoey.service;

import com.zoey.domain.Ebook;
import com.zoey.domain.EbookExample;
import com.zoey.mapper.EbookMapper;
import com.zoey.reps.EbookResp;
import com.zoey.req.EbookReq;
import com.zoey.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbookService {

    @Autowired
    private EbookMapper ebookMapper;

//    public List<Ebook> getList() {
//        return ebookMapper.selectByExample(null);
//        //return ebookMapper.selectByPrimaryKey();
//    }

    public List<EbookResp> getList(EbookReq ebookReq) {
        EbookExample example = new EbookExample();
        EbookExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%" + ebookReq.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(example);


        // List<EbookResp> ebookResps = new ArrayList<>();
        List<EbookResp> list = CopyUtil.copyList(ebookList, EbookResp.class);
//        for (Ebook ebook : ebookList) {
//            EbookResp ebookResp = new EbookResp();
//            BeanUtils.copyProperties(ebook,ebookResp);
////            ebookResp.setId(1234L);
//            ebookResps.add(ebookResp);
//        }
        return list;
    }

    public Ebook getEbookById(long id) {
        return ebookMapper.selectByPrimaryKey(id);
    }
}
