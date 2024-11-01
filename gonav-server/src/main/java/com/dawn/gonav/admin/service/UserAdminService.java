package com.dawn.gonav.admin.service;

import com.dawn.gonav.model.vo.PageBeanVO;
import com.dawn.gonav.model.vo.UserVO;

public interface UserAdminService {
    PageBeanVO<UserVO> getUserPageList(Integer pageNum, Integer pageSize, String orderBy, String searchContent);

    void delUser(Long id);

    void updateRole(Long userId, Integer role);
}
