package com.zoey.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("branch")
    public String testBranch() {
        return "this is from hot_fix branch";
    }
}
