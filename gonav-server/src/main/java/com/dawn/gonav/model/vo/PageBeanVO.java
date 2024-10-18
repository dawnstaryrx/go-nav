package com.dawn.gonav.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ClassName: PageBean
 * Package: com.dawn.dawnblogback.pojo
 * Description:分页返回结果对象
 *
 * @Author yrx
 * @Create 2024/2/20 15:13
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBeanVO <T>{
    private Long total;//总条数
    private List<T> items;//当前页数据集合
}
