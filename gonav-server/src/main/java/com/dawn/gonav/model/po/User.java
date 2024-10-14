package com.dawn.gonav.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private String nickname;
    private Integer role;
    private String githubOpenid;
    private String linuxdoOpenid;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
