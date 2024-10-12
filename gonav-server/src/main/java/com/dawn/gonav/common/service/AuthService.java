package com.dawn.gonav.common.service;

import com.dawn.gonav.model.dto.TokenDTO;

public interface AuthService {
    TokenDTO refreshToken(String refreshToken);
}
