package com.dawn.gonav.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/public/hello")
    public String hello(){
        return "hello world public";
    }

    @RequestMapping("/private/hello")
    public String hello1(){
        return "hello world private";
    }

    @RequestMapping("/default/hello")
    public String hello2(){
        return "hello world default";
    }

}
