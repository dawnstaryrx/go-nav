package com.dawn.gonav.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryPageVO {
    private Long id;
    private Long parentId;
    private String parentName;
    private String name;
    private String description;
    private Integer weight;
    private Integer status;
    private Long createUser;
    private LocalDateTime createTime;
    private Long updateUser;
    private LocalDateTime updateTime;
}
