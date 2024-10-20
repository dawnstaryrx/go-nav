package com.dawn.gonav.common.mapper;

import com.dawn.gonav.model.po.App;
import com.dawn.gonav.model.vo.AppVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppMapper {
    void addApp(App app);

    App getAppById(Long id);

    void deleteApp(Long id);

    void updateApp(App app);

    List<AppVO> getAppVOsByUserId(Long userId, Integer status);
}
