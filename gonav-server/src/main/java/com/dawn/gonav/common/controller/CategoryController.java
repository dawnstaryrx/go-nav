package com.dawn.gonav.common.controller;

import com.dawn.gonav.common.service.CategoryService;
import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取前台分类列表，树结构展示
     * @return
     */
    @GetMapping("/public/category")
    public Result<List<CategoryVO>> getCategoryTreeList(@RequestParam Long userId) {
        List<CategoryVO> categoryTreeList = categoryService.getCategoryTreeList(userId);
        return Result.success(categoryTreeList);
    }

    /**
     * 获取后台分类列表，分页展示
     * @return
     */
    @GetMapping
    public Result getCategoryPageList(){
        return null;
    }

    public Result addCategory(){
        return null;
    }
}
