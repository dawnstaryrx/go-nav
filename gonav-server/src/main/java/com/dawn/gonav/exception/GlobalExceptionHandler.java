package com.dawn.gonav.exception;

import com.dawn.gonav.model.po.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(HttpServletResponse response, BaseException e) {
        response.setStatus(e.getHttpStatus().value());
        return createResult(e);
    }

    private Result createResult(BaseException e) {
        return Result.error(e.getMessage(), e.getCode() == null ? 1 : e.getCode());
    }
    // 处理认证异常
    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "认证失败！", 401);
    }

    // 处理授权异常
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Void> handleAccessDeniedException(AccessDeniedException e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "权限不足！");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleVaildException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer stringBuffer = new StringBuffer();
        bindingResult.getFieldErrors().forEach(item ->{
            //获取错误信息
            String message = item.getDefaultMessage();
            //获取错误的属性名字
            String field = item.getField();
            stringBuffer.append(field + ":" + message + " ");
        });
        return Result.error(stringBuffer + "");
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败！");
    }
}