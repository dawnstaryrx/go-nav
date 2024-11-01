package com.dawn.gonav.admin.service;

import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.PageBeanVO;

public interface CategoryAdminService {
    PageBeanVO<CategoryPageVO> getCategoryPageList(Integer pageNum, Integer pageSize, String orderBy, String searchContent);

    void deleteCategoryById(Long id);
}
