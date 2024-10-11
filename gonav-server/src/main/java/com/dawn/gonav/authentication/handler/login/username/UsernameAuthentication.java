package com.dawn.gonav.authentication.handler.login.username;

import com.dawn.gonav.model.dto.UserLoginDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * SpringSecurity传输登录认证的数据的载体，相当一个Dto
 * 必须是 {@link Authentication} 实现类
 * 这里选择extends{@link AbstractAuthenticationToken}，而不是直接implements Authentication,
 * 是为了少些写代码。因为{@link Authentication}定义了很多接口，我们用不上。
 */
public class UsernameAuthentication extends AbstractAuthenticationToken {
  @Getter
  @Setter
  private String username; // 前端传过来
  @Getter
  @Setter
  private String password; // 前端传过来
  private UserLoginDTO currentUser; // 认证成功后，后台从数据库获取信息

  public UsernameAuthentication() {
    // 权限，用不上，直接null
    super(null);
  }

  @Override
  public Object getCredentials() {
    // 根据SpringSecurity的设计，授权成后，Credential（比如，登录密码）信息需要被清空
    return isAuthenticated() ? null : password;
  }

  @Override
  public Object getPrincipal() {
    // 根据SpringSecurity的设计，授权成功之前，getPrincipal返回的客户端传过来的数据。授权成功后，返回当前登陆用户的信息
    return isAuthenticated() ? currentUser : username;
  }

  public UserLoginDTO getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(UserLoginDTO currentUser) {
    this.currentUser = currentUser;
  }
}
