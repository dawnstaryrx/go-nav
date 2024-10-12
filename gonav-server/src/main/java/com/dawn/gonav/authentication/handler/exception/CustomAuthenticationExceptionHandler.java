package com.dawn.gonav.authentication.handler.exception;

import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.util.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationExceptionHandler implements AuthenticationEntryPoint {

//    private final ObjectMapper objectMapper;

//    public CustomAuthenticationExceptionHandler(ObjectMapper objectMapper){
//        this.objectMapper = objectMapper;
//    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter writer = response.getWriter();
        writer.print(JSONUtil.stringify(Result.error("${authentication.fail:认证失败}", 1)));
        writer.flush();
        writer.close();
    }
}
