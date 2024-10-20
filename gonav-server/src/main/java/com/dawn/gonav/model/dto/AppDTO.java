package com.dawn.gonav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppDTO {
    private Long id;
    private String name;
    private String url;
    private String iconUrl;
    private Long categoryId;
    private String description;
    private Integer weight;
    private Integer clickCount;
    private Integer status;
}
