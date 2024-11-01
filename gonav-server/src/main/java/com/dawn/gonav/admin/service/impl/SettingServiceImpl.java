package com.dawn.gonav.admin.service.impl;

import com.dawn.gonav.admin.mapper.SettingMapper;
import com.dawn.gonav.admin.service.SettingService;
import com.dawn.gonav.model.dto.SettingDTO;
import com.dawn.gonav.model.po.Setting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {
    private final SettingMapper settingMapper;
    @Override
    public Setting getByKey(String key) {
        return settingMapper.getByKey(key);
    }

    @Override
    public void updateSetting(SettingDTO settingDTO) {
        log.info("updateSetting:{}", settingDTO);
        Setting setting = getByKey(settingDTO.getKey());
        setting.setValueVarchar(settingDTO.getValueVarchar());
        setting.setValueText(settingDTO.getValueText());
        settingMapper.updateSetting(setting);
    }
}
