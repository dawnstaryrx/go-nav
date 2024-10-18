package com.dawn.gonav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long parentId;
    private String name;
    private String description;
    private Integer weight;
    private Integer status;
}
