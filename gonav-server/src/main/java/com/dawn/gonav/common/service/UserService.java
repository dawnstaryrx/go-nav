package com.dawn.gonav.common.service;

import com.dawn.gonav.model.dto.RegisterDTO;
import com.dawn.gonav.model.po.User;

public interface UserService {
    void register(RegisterDTO registerDTO);

    void sendCode(String email, Integer type);

    void resetPwd(RegisterDTO registerDTO);

    User getUserByUsernameOrEmail(String username);
}
