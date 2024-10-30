package com.dawn.gonav.admin.service.impl;

import com.dawn.gonav.admin.service.UserAdminService;
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
        user.setRole(UserTypeEnum.BLOCK.getCode());
        userMapper.update(user);
    }
}
