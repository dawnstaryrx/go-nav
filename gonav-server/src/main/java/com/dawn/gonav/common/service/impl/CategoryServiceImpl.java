package com.dawn.gonav.common.service.impl;

import com.dawn.gonav.common.mapper.CategoryMapper;
import com.dawn.gonav.common.service.CategoryService;
import com.dawn.gonav.model.dto.CategoryDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.CategoryVO;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.util.CopyUtil;
import com.dawn.gonav.util.CurrentUserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    @Override
    public List<CategoryVO> getCategoryTreeList(Long userId) {
        List<Category> categories = findAll(userId);
        // 将 Category 转换为 CategoryVO
        List<CategoryVO> categoryVOs = categories.stream()
                .map(category -> new CategoryVO(
                        category.getId(),
                        category.getParentId(),
                        category.getName(),
                        category.getDescription(),
                        category.getWeight(),
                        category.getStatus(),
                        new ArrayList<>()
                )).toList();
        List<CategoryVO> rootCategoryVOs = categoryVOs.stream()
                .filter(categoryVO -> categoryVO.getParentId() == 0)
                .toList();
        // 为每个父分类设置子分类
        for (CategoryVO parent : rootCategoryVOs) {
            parent.setChildren(getChildren(parent, categoryVOs));
        }
        return rootCategoryVOs;
    }
    // 递归查找并设置子分类
    private List<CategoryVO> getChildren(CategoryVO parent, List<CategoryVO> allCategories) {
        List<CategoryVO> children = allCategories.stream()
                .filter(categoryVO -> categoryVO.getParentId().equals(parent.getId()))
                .toList();

        for (CategoryVO child : children) {
            child.setChildren(getChildren(child, allCategories));
        }

        return children;
    }

    @Override
    public List<Category> findAll(Long userId) {
        return categoryMapper.findAll(userId);
    }

    public List<CategoryPageVO> findAllPageVO(Long userId){
        return  categoryMapper.findAllPageVO(userId);
    }

    @Override
    public PageBeanVO<CategoryPageVO> getCategoryPageList(Integer pageNum, Integer pageSize) {
        PageBeanVO<CategoryPageVO> pageBeanVO = new PageBeanVO<>();
        PageHelper.startPage(pageNum, pageSize);
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        assert userLoginDTO != null;
        List<CategoryPageVO> categoryPageVOS = findAllPageVO(userLoginDTO.getId());
        Page<CategoryPageVO> page = (Page<CategoryPageVO>) categoryPageVOS;
        pageBeanVO.setTotal(page.getTotal());
        pageBeanVO.setItems(page.getResult());
        return pageBeanVO;
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = CopyUtil.copy(categoryDTO, Category.class);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        UserLoginDTO nowUser = CurrentUserUtil.getCurrentUser();
        assert nowUser != null;
        category.setCreateUser(nowUser.getId());
        category.setUpdateUser(nowUser.getId());
        categoryMapper.addCategory(category);
    }
}
