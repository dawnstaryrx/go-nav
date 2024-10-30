package com.dawn.gonav.model.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum UserTypeEnum {
    BLOCK(0, "封禁"),
    ADMIN(2, "管理员"),
    USER(1, "用户");

    private Integer code;
    private String desc;

    UserTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
