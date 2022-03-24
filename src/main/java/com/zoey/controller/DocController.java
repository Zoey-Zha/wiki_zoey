package com.zoey.controller;

import com.zoey.domain.Doc;
import com.zoey.reps.CommonResp;
import com.zoey.reps.PageResp;
import com.zoey.reps.DocQueryResp;
import com.zoey.req.DocQueryReq;
import com.zoey.req.DocSaveReq;
import com.zoey.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController("/doc") You should put it in RequestMapping
// @RequestMapping("/doc") // It is also OK
@RequestMapping("doc")
@RestController
public class DocController {

    @Autowired
    private DocService docService;

/*
    @RequestMapping("docList")
    public List<doc> getList() {
        return docService.getList();
    }
*/
    /*
    @RequestMapping("docList")
    public CommonResp getList() {
        CommonResp resp = new CommonResp();
        List<doc> list = docService.getList();
        resp.setContent(list);
        resp.setMessage("Get all the docs");
        return resp;
    }
     */

    @GetMapping("docList")
    public CommonResp getList(@Validated DocQueryReq docQueryReq) { // 加上@Validated 开启校验参数
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        //List<DocResp> list = docService.getList(docReq);
        PageResp<DocQueryResp> list = docService.getList(docQueryReq);
        resp.setContent(list);
        resp.setMessage("Get all the docs");
        return resp;
    }

    @GetMapping("all")
    public CommonResp all() { // 加上@Validated 开启校验参数
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        //List<DocResp> list = docService.getList(docReq);
        List<DocQueryResp> list = docService.all();
        resp.setContent(list);
        resp.setMessage("Get all the docs");
        return resp;
    }

    @PostMapping("save")
    public CommonResp save(@RequestBody @Validated DocSaveReq req) {
        CommonResp resp = new CommonResp();
        //List<DocResp> list = docService.getList(docReq);
        docService.save(req);
        resp.setMessage("Save the docs");
        return resp;
    }

    @DeleteMapping("delete/{id}")
    public CommonResp delete(@PathVariable long id) {
        CommonResp resp = new CommonResp();
        //List<DocResp> list = docService.getList(docReq);
        docService.delete(id);
        resp.setMessage("delete the docs");
        return resp;
    }

    @RequestMapping("getDoc")
    public Doc getDoc(){
        return docService.getDocById(1);
    }
}
