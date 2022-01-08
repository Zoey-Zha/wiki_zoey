package com.zoey.controller;

import com.zoey.domain.Ebook;
import com.zoey.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController("/ebook") You should put it in RequestMapping
// @RequestMapping("/ebook") // It is Ok
@RequestMapping("ebook")
@RestController
public class EbookController {

    @Autowired
    private EbookService ebookService;


    @RequestMapping("ebookList")
    public List<Ebook> getList() {
        return ebookService.getList();
    }

    @RequestMapping("getEbook")
    public Ebook getEbook(){
        return ebookService.getEbookById(1);
    }
}
