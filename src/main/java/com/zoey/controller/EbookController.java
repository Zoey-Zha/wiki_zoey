package com.zoey.controller;

import com.zoey.domain.Ebook;
import com.zoey.reps.CommonResp;
import com.zoey.reps.EbookResp;
import com.zoey.req.EbookReq;
import com.zoey.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController("/ebook") You should put it in RequestMapping
// @RequestMapping("/ebook") // It is also OK
@RequestMapping("ebook")
@RestController
public class EbookController {

    @Autowired
    private EbookService ebookService;

/*
    @RequestMapping("ebookList")
    public List<Ebook> getList() {
        return ebookService.getList();
    }
*/
    /*
    @RequestMapping("ebookList")
    public CommonResp getList() {
        CommonResp resp = new CommonResp();
        List<Ebook> list = ebookService.getList();
        resp.setContent(list);
        resp.setMessage("Get all the ebooks");
        return resp;
    }
     */

    @RequestMapping("ebookList")
    public CommonResp getList(EbookReq ebookReq) {
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.getList(ebookReq);
        resp.setContent(list);
        resp.setMessage("Get all the ebooks");
        return resp;
    }

    @RequestMapping("getEbook")
    public Ebook getEbook(){
        return ebookService.getEbookById(1);
    }
}
