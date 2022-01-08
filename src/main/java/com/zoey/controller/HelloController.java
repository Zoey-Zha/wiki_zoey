package com.zoey.controller;

import com.zoey.domain.Test;
import com.zoey.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    private TestService testService;

//    @RequestMapping(value = "/hello",method = RequestMethod.POST)
//    public String hello(String name) {
//        return "hello!" + name;
//    }

    @RequestMapping("test")
    public List<Test> getList() {
        return testService.getList();
    }
}
