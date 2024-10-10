package com.dawn.gonav.common.service;

import com.dawn.gonav.model.dto.RegisterDTO;

public interface UserService {
    void register(RegisterDTO registerDTO);

    void sendCode(String email, Integer type);

    void resetPwd(RegisterDTO registerDTO);
}
