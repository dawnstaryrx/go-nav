package com.dawn.gonav.common.controller;

import com.dawn.gonav.common.service.UserService;
import com.dawn.gonav.model.dto.RegisterDTO;
import com.dawn.gonav.model.po.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/public/user/register")
    public Result register(@RequestBody RegisterDTO registerDTO){
        userService.register(registerDTO);
        return Result.success();
    }

}
