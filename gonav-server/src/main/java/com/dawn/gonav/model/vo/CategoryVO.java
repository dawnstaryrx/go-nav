package com.dawn.gonav.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    private Long id;
    private Long parentId;
    private String name;
    private String description;
    private Integer weight;
    private Integer status;
    private List<CategoryVO> children;
}
