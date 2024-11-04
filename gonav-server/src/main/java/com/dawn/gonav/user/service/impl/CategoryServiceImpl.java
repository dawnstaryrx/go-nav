package com.dawn.gonav.user.service.impl;

import com.dawn.gonav.user.mapper.CategoryMapper;
import com.dawn.gonav.user.mapper.UserMapper;
import com.dawn.gonav.user.service.CategoryService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.CategoryDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.Category;
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
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    @Override
    public List<CategoryVO> getCategoryTreeList(String username) {
        Long userId = userMapper.findUserByUsername(username).getId();
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
                .filter(categoryVO -> categoryVO.getParentId() == null)
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
                .filter(categoryVO -> Objects.equals(parent.getId(), categoryVO.getParentId()))
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

    public List<CategoryPageVO> findAllPageVOByUserId(Long userId){
        return  categoryMapper.findAllPageVOByUserId(userId);
    }

    @Override
    public PageBeanVO<CategoryPageVO> getCategoryPageList(Integer pageNum, Integer pageSize, String searchContent, String orderBy) {
        PageBeanVO<CategoryPageVO> pageBeanVO = new PageBeanVO<>();
        PageHelper.startPage(pageNum, pageSize);
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        assert userLoginDTO != null;
//        List<CategoryPageVO> categoryPageVOS = categoryMapper.findAllPageVOByUserIdAndSearchContent(userLoginDTO.getId(), searchContent, orderBy);
        List<CategoryPageVO> categoryPageVOS = findAllPageVOByUserId(userLoginDTO.getId());
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

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null){
            ExceptionTool.throwException("id 不能为空！");
        }
        if (categoryDTO.getParentId() == categoryDTO.getId()){
            ExceptionTool.throwException("父分类不能为自身！");
        }
        Category category = CopyUtil.copy(categoryDTO, Category.class);
        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Long id) {
        if (id == null){
            ExceptionTool.throwException("id 不能为空！");
        }
        Category category = getCategoryById(id);
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        assert userLoginDTO != null;
        if (!userLoginDTO.getId().equals(category.getCreateUser())){
            ExceptionTool.throwException("只有创建者才能删除！");
        }
        categoryMapper.deleteById(id);
    }

    public Category getCategoryById(Long id){
        return categoryMapper.getCategoryById(id);
    }

    // 当前登录用户的分类列表
    @Override
    public List<Category> getCategoryList() {
        UserLoginDTO userLoginDTO = CurrentUserUtil.getCurrentUser();
        return categoryMapper.getNowUserCategoryList(userLoginDTO.getId());
    }
}
