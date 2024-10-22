package com.dawn.gonav.user.controller;

import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.util.CurrentUserUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/public/hello")
    public Result<String> hello(){
        return Result.success("hello world public");
    }

    @GetMapping("/user/hello")
    public Result<String> hello1(){
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        System.out.println(userLoginDTO.getEmail());
        System.out.println(userLoginDTO.getId());
        return Result.success("hello world user" + userLoginDTO.getId());
    }

    @GetMapping("/default/hello")
    public Result<String> hello2(){
        return Result.success("hello world default");
    }

    @GetMapping("/admin/hello")
    public Result<String> hello3(){
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        System.out.println(userLoginDTO.getEmail());
        System.out.println(userLoginDTO.getId());
        return Result.success("hello world admin" + userLoginDTO.getRole() );
    }

}
