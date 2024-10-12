package com.dawn.gonav.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;
    @Setter
    private Integer code; // 自定义一个全局唯一的code，
    public BaseException() {
        httpStatus = HttpStatus.BAD_REQUEST;
    }
    public BaseException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public BaseException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getCode() {
        return code;
    }

}

