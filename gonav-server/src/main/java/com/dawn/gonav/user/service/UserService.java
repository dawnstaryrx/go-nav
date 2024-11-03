package com.dawn.gonav.user.service;

import com.dawn.gonav.model.dto.RegisterDTO;
import com.dawn.gonav.model.dto.UserInfoDTO;
import com.dawn.gonav.model.po.User;

public interface UserService {
    void register(RegisterDTO registerDTO);

    void sendCode(String email, Integer type);

    void resetPwd(RegisterDTO registerDTO);

    User getUserByUsernameOrEmail(String username);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User getUserByOpenId(String openId, String platform);

    void createUserWithOpenId(User user, String openId, String platform);

    void updateUser(UserInfoDTO userInfoDTO);
}
