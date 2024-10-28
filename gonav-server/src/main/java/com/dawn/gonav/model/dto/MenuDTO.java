package com.dawn.gonav.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Integer id;
    private String title;
    private String url;
    private Integer type;
    private Integer weight;
}
