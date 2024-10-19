package com.dawn.gonav.common.controller;

import com.dawn.gonav.common.service.CategoryService;
import com.dawn.gonav.model.dto.CategoryDTO;
import com.dawn.gonav.model.po.Category;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.CategoryPageVO;
import com.dawn.gonav.model.vo.CategoryVO;
import com.dawn.gonav.model.vo.PageBeanVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/user/category")
    public Result<PageBeanVO<CategoryPageVO>> getCategoryPageList(
        @RequestParam Integer pageNum,
        @RequestParam Integer pageSize
    ){
        PageBeanVO<CategoryPageVO> categoryPageList = categoryService.getCategoryPageList(pageNum, pageSize);
        return Result.success(categoryPageList);
    }

    @PutMapping("/user/category")
    public Result addCategory(@RequestBody @Validated CategoryDTO categoryDTO){
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    @PostMapping("/user/category")
    public Result updateCategory(@RequestBody @Validated CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    @DeleteMapping("/user/category")
    public Result deleteCategory(@RequestParam Long id){
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
