package com.dawn.gonav.common.service;

import com.dawn.gonav.model.dto.AppDTO;
import com.dawn.gonav.model.vo.AppVO;

import java.util.List;

public interface AppService {
    void addApp(AppDTO appDTO);

    void deleteApp(Long id);

    void updateApp(AppDTO appDTO);

    List<AppVO> getAppsByUserId(Long userId);
}
