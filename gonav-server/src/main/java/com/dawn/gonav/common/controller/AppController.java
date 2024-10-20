package com.dawn.gonav.common.controller;

import com.dawn.gonav.common.service.AppService;
import com.dawn.gonav.model.dto.AppDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.AppVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;
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

    @GetMapping("/public/app")
    public Result<List<AppVO>> getAppsByUserId(@RequestParam Long userId){
        return Result.success(appService.getAppsByUserId(userId));
    }
}
