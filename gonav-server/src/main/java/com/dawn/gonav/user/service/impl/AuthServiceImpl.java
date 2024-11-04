package com.dawn.gonav.user.service.impl;

import com.dawn.gonav.user.service.AuthService;
import com.dawn.gonav.user.service.JwtService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.TokenDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.util.TimeTool;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.dawn.gonav.model.constant.RedisConstant.USER_REFRESH_TOKEN_PREFIX;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public TokenDTO refreshToken(String refreshToken) {
        // 验证refreshToken的有效性
        UserLoginDTO userLoginDTO = jwtService.verifyJwt(refreshToken, UserLoginDTO.class);
        if (userLoginDTO == null){
            ExceptionTool.throwException("refreshToken无效", 401);
        }
        assert userLoginDTO != null;
        String redisKey = USER_REFRESH_TOKEN_PREFIX + userLoginDTO.getEmail();
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        // 判断refreshToken是否一致
        String redisRefreshToken = operations.get(redisKey);
        if (!refreshToken.equals(redisRefreshToken)){
            ExceptionTool.throwException("refreshToken无效", 401);
        }
        // 刷新token
        System.out.println("刷新token------------------");
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(generateToken(userLoginDTO));
        tokenDTO.setRefreshToken(generateRefreshToken(userLoginDTO));
        // 修改redis中的refreshToken
        operations.set(redisKey, tokenDTO.getRefreshToken(), 30, TimeUnit.DAYS);
        return tokenDTO;
    }
    private String generateToken(UserLoginDTO currentUser) {
        // todo 10
        long expiredTime = TimeTool.nowMilli() + TimeUnit.MINUTES.toMillis(10); // 10分钟后过期
        currentUser.setExpiredTime(expiredTime);
        return jwtService.createJwt(currentUser, expiredTime);
    }

    private String generateRefreshToken(UserLoginDTO loginInfo) {
        return jwtService.createJwt(loginInfo, TimeTool.nowMilli() + TimeUnit.DAYS.toMillis(30));
    }
}
