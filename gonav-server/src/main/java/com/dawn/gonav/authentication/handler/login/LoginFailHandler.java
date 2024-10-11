package com.dawn.gonav.authentication.handler.login;


import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.util.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * AbstractAuthenticationProcessingFilter抛出AuthenticationException异常后，会跑到这里来
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    String errorMessage = exception.getMessage();
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    PrintWriter writer = response.getWriter();
//    Result responseData = ResultBuilder.aResult()
//        .data(null)
//        .code("login.fail")
//        .msg(errorMessage)
//        .build();
    Result responseData = Result.error(errorMessage);
    writer.print(JSONUtil.stringify(responseData));
    writer.flush();
    writer.close();
  }
}
