package com.dawn.gonav.user.mapper;

import com.dawn.gonav.model.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void add(User user);

    User findUserByEmail(String email);

    void update(User userByEmail);

    User findUserByUsername(String usernameOrEmail);

    User findUserByLinuxDoOpenId(String openId);

    User findUserByGitHubOpenId(String openId);
}
