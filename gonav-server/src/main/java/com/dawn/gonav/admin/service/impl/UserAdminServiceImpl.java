package com.dawn.gonav.admin.service.impl;

import com.dawn.gonav.admin.service.UserAdminService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.enums.UserTypeEnum;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.model.vo.UserVO;
import com.dawn.gonav.user.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserMapper userMapper;
    @Override
    public PageBeanVO<UserVO> getUserPageList(Integer pageNum, Integer pageSize, String orderBy, String searchContent) {
        PageBeanVO<UserVO> pageBeanVO = new PageBeanVO<>();
        PageHelper.startPage(pageNum, pageSize);
        List<UserVO> userVOS = userMapper.getUserVOs(orderBy, searchContent);
        Page<UserVO> page = (Page<UserVO>) userVOS;
        pageBeanVO.setTotal(page.getTotal());
        pageBeanVO.setItems(page.getResult());
        return pageBeanVO;
    }

    @Override
    public void delUser(Long id) {
        User user = userMapper.findUserById(id);
        if (Objects.equals(user.getUsername(), "admin")){
            throw new RuntimeException("你还想删除本大王？");
        }
        user.setRole(UserTypeEnum.BLOCK.getCode());
        userMapper.update(user);
    }

    @Override
    public void updateRole(Long userId, Integer role) {
        if (
                !Objects.equals(role, UserTypeEnum.ADMIN.getCode()) &&
                !Objects.equals(role, UserTypeEnum.USER.getCode()) &&
                !Objects.equals(role, UserTypeEnum.BLOCK.getCode())
        ){
            ExceptionTool.throwException("角色不存在");
        }
        User user = userMapper.findUserById(userId);
        if (Objects.equals(user.getUsername(), "admin")){
            throw new RuntimeException("你还想改本大王的角色？");
        }
        user.setRole(role);
        userMapper.update(user);
    }
}
