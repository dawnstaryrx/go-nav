package com.dawn.gonav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    private String sessionId; // 会话id，全局唯一
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private Integer role;
    private String wechatOpenid;
    private Long expiredTime; // 过期时间
}
