package com.dawn.gonav.user.service.impl;

import com.dawn.gonav.model.po.User;
import com.dawn.gonav.user.mapper.AppMapper;
import com.dawn.gonav.user.mapper.UserMapper;
import com.dawn.gonav.user.service.AppService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.AppDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.enums.AppStatusEnum;
import com.dawn.gonav.model.enums.UserTypeEnum;
import com.dawn.gonav.model.po.App;
import com.dawn.gonav.model.vo.AppVO;
import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.util.CopyUtil;
import com.dawn.gonav.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppServiceImpl implements AppService {
    private final AppMapper appMapper;
    private final UserService userService;
    @Override
    public void addApp(AppDTO appDTO) {
        App app = CopyUtil.copy(appDTO, App.class);
        if (app.getName() == null || app.getName().trim().isEmpty()){
            ExceptionTool.throwException("应用名称不能为空");
        }
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        if (nowUser == null){
            ExceptionTool.throwException("用户未登录");
        }
        assert nowUser != null;
        app.setCreateUser(nowUser.getId());
        app.setUpdateUser(nowUser.getId());
        app.setCreateTime(LocalDateTime.now());
        app.setUpdateTime(LocalDateTime.now());
        app.setClickCount(0);
        appMapper.addApp(app);
    }

    @Override
    public void deleteApp(Long id) {
        App app = getAppById(id);
        if (app == null){
            ExceptionTool.throwException("应用不存在");
        }
        appMapper.deleteApp(id);
    }

    @Override
    public void updateApp(AppDTO appDTO) {
        App app = CopyUtil.copy(appDTO, App.class);
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        if (nowUser == null){
            ExceptionTool.throwException("用户未登录");
        }
        assert nowUser != null;
        app.setUpdateUser(nowUser.getId());
        app.setUpdateTime(LocalDateTime.now());
        appMapper.updateApp(app);
    }

    @Override
    public List<AppVO> getAppsByUsername(String username, Long categoryId) {
        Integer status = AppStatusEnum.ENABLE.getCode();
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        log.info("----------------status 启用");
        if (nowUser != null && nowUser.getRole() == 1) {
            log.info("----------------status 隐藏");
            status = AppStatusEnum.HIDE.getCode();
        }
        if (nowUser != null && Objects.equals(nowUser.getUsername(), username)){
            log.info("----------------status 私有");
            status = AppStatusEnum.PRIVATE.getCode();
        }
        if (nowUser != null && Objects.equals(nowUser.getRole(), UserTypeEnum.ADMIN.getCode())){
            log.info("----------------status 私有");
            status = AppStatusEnum.PRIVATE.getCode();
        }
        User user = userService.getUserByUsername(username);
        List<AppVO> appVOS = appMapper.getAppVOsByUserId(user.getId(), status, categoryId);
        return appVOS;
    }

    public App getAppById(Long id){
        return appMapper.getAppById(id);
    }
}
