package com.dawn.gonav.common.service;

import com.dawn.gonav.model.dto.CategoryDTO;
import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.CategoryVO;
import com.dawn.gonav.model.vo.PageBeanVO;

import java.util.List;

public interface CategoryService {
    List<CategoryVO> getCategoryTreeList(Long userId);

    List<Category> findAll(Long userId);

    PageBeanVO<CategoryPageVO> getCategoryPageList(Integer pageNum, Integer pageSize);

    void addCategory(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
