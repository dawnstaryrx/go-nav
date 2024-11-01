package com.dawn.gonav.admin.controller;

import com.dawn.gonav.admin.service.SettingService;
import com.dawn.gonav.model.dto.SettingDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.po.Setting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;
    @GetMapping("/public/setting")
    public Result<Setting> getSettingByKey(@RequestParam String key) {
        Setting setting = settingService.getByKey(key);
        return Result.success(setting);
    }

    @PostMapping("/admin/setting")
    public Result updateSetting(@RequestBody SettingDTO settingDTO) {
        settingService.updateSetting(settingDTO);
        return Result.success();
    }
}
