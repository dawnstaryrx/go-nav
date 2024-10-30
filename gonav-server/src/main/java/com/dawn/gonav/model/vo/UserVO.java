package com.dawn.gonav.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String githubOpenid;
    private String linuxdoOpenid;
    private String phone;
    private String nickname;
    private Integer role;
    private LocalDateTime createTime;
    private Integer appNum; // 该用户的app数
    private Integer categoryNum; // 该用户的category数
}
