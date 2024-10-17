package com.dawn.gonav.common.mapper;

import com.dawn.gonav.model.po.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAll(Long userId);
}
