package com.dawn.gonav.common.mapper;

import com.dawn.gonav.model.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void add(User user);

    User findUserByEmail(String email);

    void update(User userByEmail);
}
