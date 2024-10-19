package com.dawn.gonav.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private Long parentId;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer weight;
    @NotNull
    private Integer status;
}
