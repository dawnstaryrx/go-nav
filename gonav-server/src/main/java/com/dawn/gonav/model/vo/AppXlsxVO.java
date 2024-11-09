package com.dawn.gonav.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppXlsxVO {
    @ExcelProperty("应用名称")
    private String name;
    @ExcelProperty("应用地址")
    private String url;
    @ExcelProperty("图标地址")
    private String iconUrl;
    @ExcelProperty("分类id")
    private Long categoryId;
    @ExcelProperty("描述信息")
    private String description;
    @ExcelProperty("权重")
    private Integer weight;
    @ExcelProperty("状态（1公开，2登录后可见，3本人可见，4不可见）")
    private Integer status;
    @ExcelProperty("分类名称")
    private String categoryName;
}
