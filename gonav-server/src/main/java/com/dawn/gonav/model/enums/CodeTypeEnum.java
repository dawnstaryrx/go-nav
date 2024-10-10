package com.dawn.gonav.model.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum CodeTypeEnum {
    REGISTER_CODE(1, "注册验证码"),
    LOGIN_CODE(2, "登录验证码"),
    RESET_CODE(3, "重置密码验证码");

    private final Integer type;
    private final String desc;

    CodeTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static CodeTypeEnum getByType(Integer type) {
        for (CodeTypeEnum codeType : CodeTypeEnum.values()) {
            if (codeType.getType().equals(type)) {
                return codeType;
            }
        }
        return null;
    }


}
