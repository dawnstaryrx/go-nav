package com.dawn.gonav.exception;

import com.dawn.gonav.model.po.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleVaildException(MethodArgumentNotValidException e){

        log.error("数据校验出现问题：{}，异常类型：{}",e.getMessage(),e.getClass());
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
}
