package com.dawn.gonav.admin.service.impl;

import com.dawn.gonav.admin.mapper.CategoryAdminMapper;
import com.dawn.gonav.admin.service.CategoryAdminService;
import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {
    private final CategoryAdminMapper categoryAdminMapper;
    @Override
    public PageBeanVO<CategoryPageVO> getCategoryPageList(Integer pageNum, Integer pageSize) {
        PageBeanVO<CategoryPageVO> pageBeanVO = new PageBeanVO<>();
        PageHelper.startPage(pageNum, pageSize);
        List<CategoryPageVO> categoryPageVOS = categoryAdminMapper.findAllPageVO();
        Page<CategoryPageVO> page = (Page<CategoryPageVO>) categoryPageVOS;
        pageBeanVO.setTotal(page.getTotal());
        pageBeanVO.setItems(page.getResult());
        return pageBeanVO;
    }
}
