package com.dawn.gonav.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Setting {
    private Integer id;
    private String key;
    private String valueVarchar;
    private String valueText;
}
