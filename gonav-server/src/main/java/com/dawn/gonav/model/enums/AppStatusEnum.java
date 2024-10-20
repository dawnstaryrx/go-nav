package com.dawn.gonav.model.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum AppStatusEnum {
    // 1 启用：所有用户可见
    // 2 隐藏：登录后用户可见
    // 3 私有：当前用户可见
    // 4 禁用：所有用户不可见
    DISABLE(4, "禁用"),
    ENABLE(1, "启用"),
    HIDE(2, "隐藏"),
    PRIVATE(3, "私有");

    private Integer code;
    private String desc;

    AppStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
