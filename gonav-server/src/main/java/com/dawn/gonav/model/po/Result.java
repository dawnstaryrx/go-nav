package com.dawn.gonav.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;//业务状态码  0-成功  1-失败 401-token过期 403-权限不足
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(1, message, null);
    }

    public static Result error(String message, Integer code) {
        return new Result(code, message, null);
    }
}
