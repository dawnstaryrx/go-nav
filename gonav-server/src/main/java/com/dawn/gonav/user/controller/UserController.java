package com.dawn.gonav.user.controller;

import com.dawn.gonav.model.dto.UserInfoDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.model.dto.RegisterDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 发送验证码
     * @param email:邮箱
     * @param type:发送验证码类型的枚举 CodeTypeEnum.type
     * @return
     */
    @PostMapping("/public/sendCode")
    public Result sendCode(@RequestParam String email, @RequestParam Integer type){
        userService.sendCode(email, type);
        return Result.success();
    }

    /**
     * 注册
     * @param registerDTO
     * @return
     */
    @PostMapping("/public/user/register")
    public Result register(@RequestBody RegisterDTO registerDTO){
        userService.register(registerDTO);
        return Result.success();
    }

    /**
     * 重置密码，使用registerDTO
     * @param registerDTO
     * @return
     */
    @PostMapping("/public/user/resetPwd")
    public Result resetPwd(@RequestBody RegisterDTO registerDTO){
        userService.resetPwd(registerDTO);
        return Result.success();
    }

    @PostMapping("/user/user")
    public Result updateUser(@RequestBody UserInfoDTO userInfoDTO){
        userService.updateUser(userInfoDTO);
        return Result.success();
    }

    @GetMapping("/user/current")
    public Result<UserLoginDTO> getCurrentUser(){
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        return Result.success(userLoginDTO);
    }

}
