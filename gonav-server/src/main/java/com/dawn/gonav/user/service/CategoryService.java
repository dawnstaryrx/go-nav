package com.dawn.gonav.user.service;

import com.dawn.gonav.model.dto.CategoryDTO;
import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.CategoryVO;
import com.dawn.gonav.model.vo.PageBeanVO;

import java.util.List;

public interface CategoryService {
    List<CategoryVO> getCategoryTreeList(String username);

    List<Category> findAll(Long userId);

    PageBeanVO<CategoryPageVO> getCategoryPageList(Integer pageNum, Integer pageSize, String searchContent, String orderBy);

    void addCategory(CategoryDTO categoryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    List<Category> getCategoryList();
}
