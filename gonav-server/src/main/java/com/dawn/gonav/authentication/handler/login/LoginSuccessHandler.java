package com.dawn.gonav.authentication.handler.login;

import com.dawn.gonav.user.service.JwtService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.util.JSONUtil;
import com.dawn.gonav.util.TimeTool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.dawn.gonav.model.constant.RedisConstant.USER_REFRESH_TOKEN_PREFIX;

/**
 * 认证成功/登录成功 事件处理器
 */
@Component
public class LoginSuccessHandler extends
    AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  public LoginSuccessHandler() {
    this.setRedirectStrategy(new RedirectStrategy() {
      @Override
      public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
          throws IOException {
        // 更改重定向策略，前后端分离项目，后端使用RestFul风格，无需做重定向
        // Do nothing, no redirects in REST
      }
    });
  }

  @Override
  public void onAuthenticationSuccess(
          HttpServletRequest request,
          HttpServletResponse response,
          Authentication authentication
  ) throws IOException {
    Object principal = authentication.getPrincipal();
    if (principal == null || !(principal instanceof UserLoginDTO)) {
      ExceptionTool.throwException("登陆认证成功后，authentication.getPrincipal()返回的Object对象必须是：UserLoginInfo！");
    }
    UserLoginDTO currentUser = (UserLoginDTO) principal;
    currentUser.setSessionId(UUID.randomUUID().toString());

    // 生成token和refreshToken
    Map<String, Object> responseData = new LinkedHashMap<>();
    responseData.put("token", generateToken(currentUser));
    responseData.put("refreshToken", generateRefreshToken(currentUser));
    // 将refreshToken存放到Redis中，并设置过期时间
    stringRedisTemplate.opsForValue().set(USER_REFRESH_TOKEN_PREFIX + currentUser.getEmail(), responseData.get("refreshToken").toString(), 30, TimeUnit.DAYS);
    // 一些特殊的登录参数。比如三方登录，需要额外返回一个字段是否需要跳转的绑定已有账号页面
    Object details = authentication.getDetails();
    if (details instanceof Map) {
      Map detailsMap = (Map)details;
      responseData.putAll(detailsMap);
    }

    // 虽然APPLICATION_JSON_UTF8_VALUE过时了，但也要用。因为Postman工具不声明utf-8编码就会出现乱码
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    PrintWriter writer = response.getWriter();
//    writer.print(JSON.stringify(Result.data(responseData, "${login.success:登录成功！}")));
    writer.print(JSONUtil.stringify(Result.success(responseData)));
    writer.flush();
    writer.close();
  }

  public String generateToken(UserLoginDTO currentUser) {
    // todo 1分种
    long expiredTime = TimeTool.nowMilli() + TimeUnit.MINUTES.toMillis(10); // 10分钟后过期
    currentUser.setExpiredTime(expiredTime);
    return jwtService.createJwt(currentUser, expiredTime);
  }

  private String generateRefreshToken(UserLoginDTO loginInfo) {
    return jwtService.createJwt(loginInfo, TimeTool.nowMilli() + TimeUnit.DAYS.toMillis(30));
  }

}
