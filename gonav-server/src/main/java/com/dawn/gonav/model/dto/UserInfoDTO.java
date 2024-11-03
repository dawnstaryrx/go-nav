package com.dawn.gonav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private String githubOpenid;
    private String linuxdoOpenid;
    private LocalDateTime createTime;
}
