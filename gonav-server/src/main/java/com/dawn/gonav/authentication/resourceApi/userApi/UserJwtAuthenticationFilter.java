package com.dawn.gonav.authentication.resourceApi.userApi;

import com.dawn.gonav.user.service.JwtService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.UserLoginDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserJwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;

    public UserJwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(jwtToken)) {
            ExceptionTool.throwException("jwt为空");
        }
        if (jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }
        UserLoginDTO userLoginInfo = null;
        try {
            userLoginInfo = jwtService.verifyJwt(jwtToken, UserLoginDTO.class);
            UserJwtAuthentication authentication = new UserJwtAuthentication();
            authentication.setJwtToken(jwtToken);
            authentication.setAuthenticated(true); // 设置true，认证通过。
            authentication.setCurrentUser(userLoginInfo);
            // 认证通过后，一定要设置到SecurityContextHolder里面去。
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (ExpiredJwtException e) {
            // 转换异常，指定code，让前端知道时token过期，去调刷新token接口
            ExceptionTool.throwException("jwt过期", HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            ExceptionTool.throwException("jwt无效", HttpStatus.UNAUTHORIZED.value());
        }
        if (0==userLoginInfo.getRole()){
            ExceptionTool.throwException("用户权限不足！", 403);
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
