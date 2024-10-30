package com.dawn.gonav.user.controller;

import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.user.service.AppService;
import com.dawn.gonav.model.dto.AppDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.AppVO;
import com.dawn.gonav.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;
    private final UserService userService;
    @PutMapping("/user/app")
    public Result addApp(@RequestBody AppDTO appDTO){
        appService.addApp(appDTO);
        return Result.success();
    }

    // TODO Excel批量导入
    @PutMapping("/user/apps")
    public Result addApps(){
        return Result.success();
    }

    @DeleteMapping("/user/app")
    public Result deleteApp(@RequestParam Long id){
        appService.deleteApp(id);
        return Result.success();
    }

    @PostMapping("/user/app")
    public Result updateApp(@RequestBody AppDTO appDTO){
        appService.updateApp(appDTO);
        return Result.success();
    }

    @GetMapping("/user/app/page")
    public Result<PageBeanVO<AppVO>> getAppsPageNowUser(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String searchContent
    ){
        return Result.success(appService.getAppsPageNowUser(pageNum, pageSize, categoryId, status, searchContent));
    }

    @GetMapping("/public/app")
    public Result<List<AppVO>> getAppsByUsernamePublic(@RequestParam String username, @RequestParam(required = false) Long categoryId){
        return Result.success(appService.getAppsByUsername(username, categoryId));
    }
    @GetMapping("/user/app")
    public Result<List<AppVO>> getAppsByUsernameUser(@RequestParam String username, @RequestParam(required = false) Long categoryId){
        return Result.success(appService.getAppsByUsername(username, categoryId));
    }

    @GetMapping("/public/app/index")
    public Result<List<AppVO>> getAppsIndexPublic(@RequestParam(required = false) Long categoryId){
        User user = userService.getUserByUsername("admin");
        if (user == null || user.getId() == null){
            ExceptionTool.throwException("当前无ADMIN用户！");
        }
        assert user != null;
        return getAppsByUsernamePublic(user.getUsername(), categoryId);
    }

    @GetMapping("/user/app/index")
    public Result<List<AppVO>> getAppsIndexUser(@RequestParam(required = false) Long categoryId){
        User user = userService.getUserByUsername("admin");
        if (user == null || user.getId() == null){
            ExceptionTool.throwException("当前无ADMIN用户！");
        }
        assert user != null;
        return getAppsByUsernameUser(user.getUsername(), categoryId);
    }

    @PostMapping("/public/app/click")
    public Result clickApp(@RequestParam Long id){
        appService.clickApp(id);
        return Result.success();
    }
}
