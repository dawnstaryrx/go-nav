package com.dawn.gonav.user.mapper;

import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.vo.CategoryPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAll(Long userId);

    void addCategory(Category category);

    List<CategoryPageVO> findAllPageVOByUserId(Long userId);

    void deleteById(Long id);

    void updateCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getNowUserCategoryList(Long userId);

    List<CategoryPageVO> findAllPageVOByUserIdAndSearchContent(Long id, String searchContent, String orderBy);
}
