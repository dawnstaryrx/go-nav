package com.dawn.gonav.admin.controller;

import com.dawn.gonav.admin.service.CategoryAdminService;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.PageBeanVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class CategoryAdminController {
    private final CategoryAdminService categoryAdminService;
    @GetMapping
    public Result<PageBeanVO<CategoryPageVO>> getCategoryPageList(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize
    ) {
        PageBeanVO<CategoryPageVO> categoryPageList = categoryAdminService.getCategoryPageList(pageNum, pageSize);
        return Result.success(categoryPageList);
    }
}
