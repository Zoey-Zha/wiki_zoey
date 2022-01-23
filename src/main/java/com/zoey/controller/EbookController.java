package com.zoey.controller;

import com.zoey.domain.Ebook;
import com.zoey.reps.CommonResp;
import com.zoey.reps.EbookQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.EbookQueryReq;
import com.zoey.req.EbookSaveReq;
import com.zoey.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("ebookList")
    public CommonResp getList(@Validated EbookQueryReq ebookQueryReq) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        //List<EbookResp> list = ebookService.getList(ebookReq);
        PageResp<EbookQueryResp> list = ebookService.getList(ebookQueryReq);
        resp.setContent(list);
        resp.setMessage("Get all the ebooks");
        return resp;
    }

    @PostMapping("save")
    public CommonResp save(@RequestBody @Validated EbookSaveReq req) {
        CommonResp resp = new CommonResp();
        //List<EbookResp> list = ebookService.getList(ebookReq);
        ebookService.save(req);
        resp.setMessage("Save the ebooks");
        return resp;
    }

    @DeleteMapping("delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp();
        //List<EbookResp> list = ebookService.getList(ebookReq);
        ebookService.delete(id);
        resp.setMessage("delete the ebooks");
        return resp;
    }

    @RequestMapping("getEbook")
    public Ebook getEbook(){
        return ebookService.getEbookById(1);
    }
}
