package com.zoey.controller;

import com.zoey.domain.Demo;
import com.zoey.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String hello(String name) {
        return "hello!" + name;
    }

    @RequestMapping("demo")
    public List<Demo> getList() {
        return demoService.getList();
    }

    @RequestMapping("getDemo")
    public Demo getDemo(){
        return demoService.getDemoById(1001);
    }
}
