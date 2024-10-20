package com.dawn.gonav.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppVO {
    private Long id;
    private String name;
    private String url;
    private String iconUrl;
    private Long categoryId;
    private String categoryName;
    private String description;
    private Integer weight;
    private Integer clickCount;
}
