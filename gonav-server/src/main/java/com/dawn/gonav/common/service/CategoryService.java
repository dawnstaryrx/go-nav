package com.dawn.gonav.common.service;

import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    List<CategoryVO> getCategoryTreeList(Long userId);

    List<Category> findAll(Long userId);
}
