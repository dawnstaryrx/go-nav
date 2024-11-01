package com.dawn.gonav.admin.controller;

import com.dawn.gonav.admin.service.UserAdminService;
import com.dawn.gonav.model.po.Result;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.model.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserAdminController {
    private final UserAdminService userAdminService;
    @GetMapping("/page")
    public Result<PageBeanVO<UserVO>> getUserPageList(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String searchContent) {
        PageBeanVO<UserVO> userVOPageBeanVO = userAdminService.getUserPageList(pageNum, pageSize, orderBy, searchContent);
        return Result.success(userVOPageBeanVO);
    }

    @DeleteMapping
    public Result delUser(@RequestParam Long id) {
        // update用户的role
        userAdminService.delUser(id);
        return Result.success();
    }

    @PostMapping
    public Result updateRole(@RequestParam Integer role, @RequestParam Long userId){
        userAdminService.updateRole(userId, role);
        return Result.success();
    }
}
