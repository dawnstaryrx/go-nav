package com.dawn.gonav.authentication.handler.login.github;

import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.exception.BaseException;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 帐号密码登录认证
 */
@Component
public class GitHubAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Autowired
  private GitHubApiClient gitHubApiClient;

  public static final String PLATFORM = "GitHub";

  public GitHubAuthenticationProvider() {
    super();
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String code = (String) authentication.getCredentials();
    System.out.println("GitHub code: " + code);
    try {
      System.out.println("get token...");
      String token = gitHubApiClient.getTokenByCode(code);
      System.out.println("GitHub token: " + token);
      if (token == null) {
        // 乱传code过来。用户根本没授权！
        ExceptionTool.throwException("授权失败！");
      }
      System.out.println("GitHub thirdUser: 111");
      Map<String, Object> thirdUser = gitHubApiClient.getThirdUserInfo(token);
      if (thirdUser == null) {
        // 未知异常。获取不到用户openId，也就无法继续登录了
        ExceptionTool.throwException("授权失败！");
      }
      assert thirdUser != null;
      String openId = thirdUser.get("openId").toString();
      // 通过第三方的账号唯一id，去匹配数据库中已有的账号信息
      User user = userService.getUserByOpenId(openId, PLATFORM);
      boolean notBindAccount = user == null; // linuxdo账号没有绑定我们系统的用户
      if (notBindAccount) {
        // 没找到账号信息，那就是第一次使用linuxdo登录，可能需要创建一个新用户
        user = new User();
        userService.createUserWithOpenId(user, openId, PLATFORM);
        user = userService.getUserByOpenId(openId, PLATFORM);
      }
      GitHubAuthentication successAuth = new GitHubAuthentication();
      successAuth.setCurrentUser(JSONUtil.convert(user, UserLoginDTO.class));
      successAuth.setAuthenticated(true); // 认证通过，一定要设成true

      HashMap<String, Object> loginDetail = new HashMap<>();
      // 第一次使用三方账号登录，需要告知前端，让前端跳转到初始化账号页面（可能需要）
      loginDetail.put("needInitUserInfo", notBindAccount);
      loginDetail.put("nickname", thirdUser.get("nickname").toString()); // sayHello
      successAuth.setDetails(loginDetail);
      return successAuth;
    } catch (BaseException e) {
      // 转换已知异常，将异常内容返回给前端
      throw new BadCredentialsException(e.getMessage());
    } catch (Exception e) {
      // 未知异常
      throw new BadCredentialsException("GitHub Authentication Failed");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(GitHubAuthentication.class);
  }
}

