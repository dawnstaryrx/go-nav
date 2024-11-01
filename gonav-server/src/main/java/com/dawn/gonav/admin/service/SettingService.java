package com.dawn.gonav.admin.service;

import com.dawn.gonav.model.dto.SettingDTO;
import com.dawn.gonav.model.po.Setting;
import org.springframework.stereotype.Service;

@Service
public interface SettingService {
    Setting getByKey(String key);

    void updateSetting(SettingDTO settingDTO);
}
