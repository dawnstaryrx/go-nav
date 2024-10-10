package com.dawn.gonav.common.service.impl;

import com.dawn.gonav.common.mapper.UserMapper;
import com.dawn.gonav.common.service.UserService;
import com.dawn.gonav.exception.BusinessException;
import com.dawn.gonav.model.dto.RegisterDTO;
import com.dawn.gonav.model.po.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Override
    public void register(RegisterDTO registerDTO) {
        // 根据邮箱查询用户是否存在
        User userByEmail = userMapper.findUserByEmail(registerDTO.getEmail());
        if (userByEmail != null){
            throw new BusinessException("用户已存在");
        }
        User user = new User();
        user.setUsername(registerDTO.getEmail());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.add(user);
    }
}
