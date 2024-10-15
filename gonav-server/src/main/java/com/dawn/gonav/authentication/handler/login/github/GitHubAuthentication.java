package com.dawn.gonav.authentication.handler.login.github;

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
public class GitHubAuthentication extends AbstractAuthenticationToken {
  @Getter
  @Setter
  private String code; // 前端传过来
  private UserLoginDTO currentUser; // 认证成功后，后台从数据库获取信息

  public GitHubAuthentication() {
    // 权限，用不上，直接null
    super(null);
  }

  @Override
  public Object getCredentials() {
    return isAuthenticated() ? null : code;
  }

  @Override
  public Object getPrincipal() {
    return isAuthenticated() ? currentUser : null;
  }

  public UserLoginDTO getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(UserLoginDTO currentUser) {
    this.currentUser = currentUser;
  }
}
