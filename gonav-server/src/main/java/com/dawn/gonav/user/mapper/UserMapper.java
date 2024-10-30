package com.dawn.gonav.user.mapper;

import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    void add(User user);

    User findUserByEmail(String email);

    void update(User userByEmail);

    User findUserByUsername(String usernameOrEmail);

    User findUserById(Long id);

    User findUserByLinuxDoOpenId(String openId);

    User findUserByGitHubOpenId(String openId);

    List<UserVO> getUserVOs(String orderBy, String searchContent);
}
