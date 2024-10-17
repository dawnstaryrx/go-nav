package com.dawn.gonav.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class App {
    private Long id;
    private String name;
    private String url;
    private String iconUrl;
    private Long categoryId;
    private String description;
    private Integer weight;
    private Integer clickCount;
    private Integer status;
    private Long createUser;
    private LocalDateTime createTime;
    private Long updateUser;
    private LocalDateTime updateTime;
}
