package com.zoey.controller;

import com.zoey.reps.CommonResp;
import com.zoey.reps.DocQueryResp;
import com.zoey.reps.PageResp;
import com.zoey.req.DocQueryReq;
import com.zoey.req.DocSaveReq;
import com.zoey.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

// @RestController("/doc") You should put it in RequestMapping
// @RequestMapping("/doc") // It is also OK
@RequestMapping("doc")
@RestController
public class DocController {

    @Autowired
    private DocService docService;


    @GetMapping("docList")
    public CommonResp getList(@Validated DocQueryReq docQueryReq) { // 加上@Validated 开启校验参数
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        //List<DocResp> list = docService.getList(docReq);
        PageResp<DocQueryResp> list = docService.getList(docQueryReq);
        resp.setContent(list);
        resp.setMessage("Get all the docs");
        return resp;
    }

    // all前不应该有"/"
    @GetMapping("all/{ebookId}")
    public CommonResp all(@PathVariable String ebookId) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
        List<DocQueryResp> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }

//    @GetMapping("all")
//    public CommonResp all() { // 加上@Validated 开启校验参数
//        CommonResp<List<DocQueryResp>> resp = new CommonResp<>();
//        //List<DocResp> list = docService.getList(docReq);
//        List<DocQueryResp> list = docService.all();
//        resp.setContent(list);
//        resp.setMessage("Get all the docs");
//        return resp;
//    }

    @PostMapping("save")
    public CommonResp save(@RequestBody @Validated DocSaveReq req) {
        CommonResp resp = new CommonResp();
        //List<DocResp> list = docService.getList(docReq);
        docService.save(req);
        resp.setMessage("Save the docs");
        return resp;
    }

    @DeleteMapping("delete/{ids}")
    public CommonResp delete(@PathVariable String ids) {
        CommonResp resp = new CommonResp();
        //List<DocResp> list = docService.getList(docReq);

        List<String> strings = Arrays.asList(ids.split(","));
        docService.delete(strings);
        resp.setMessage("delete the docs");
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp<>();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }


    @GetMapping("/find-content/{id}")
    public CommonResp findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp();
        docService.vote(id);
        return commonResp;
    }



}
