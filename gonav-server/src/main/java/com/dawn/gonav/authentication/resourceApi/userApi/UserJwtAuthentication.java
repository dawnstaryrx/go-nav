package com.dawn.gonav.authentication.resourceApi.userApi;

import com.dawn.gonav.model.dto.UserLoginDTO;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserJwtAuthentication extends AbstractAuthenticationToken {
    private String jwtToken; // 前端传过来
    private UserLoginDTO currentUser; // 认证成功后，后台从数据库获取信息

    public UserJwtAuthentication() {
        // 权限，用不上，直接null
        super(null);
    }

    @Override
    public Object getCredentials() {
        // 根据SpringSecurity的设计，授权成后，Credential（比如，登录密码）信息需要被清空
        return isAuthenticated() ? null : jwtToken;
    }

    @Override
    public Object getPrincipal() {
        // 根据SpringSecurity的设计，授权成功之前，getPrincipal返回的客户端传过来的数据。授权成功后，返回当前登陆用户的信息
        return isAuthenticated() ? currentUser : jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public UserLoginDTO getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserLoginDTO currentUser) {
        this.currentUser = currentUser;
    }
}
