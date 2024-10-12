package com.dawn.gonav.common.controller;

import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.util.CurrentUserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/public/hello")
    public String hello(){
        return "hello world public";
    }

    @RequestMapping("/user/hello")
    public String hello1(){
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        System.out.println(userLoginDTO.getEmail());
        System.out.println(userLoginDTO.getId());
        return "hello world private";
    }

    @RequestMapping("/default/hello")
    public String hello2(){
        return "hello world default";
    }

    @RequestMapping("/admin/hello")
    public String hello3(){
        return "hello world admin";
    }

}
