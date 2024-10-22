package com.dawn.gonav;

import com.dawn.gonav.user.service.JwtService;
import com.dawn.gonav.model.dto.UserLoginDTO;
import org.junit.jupiter.api.Test;

public class RefreshTokenTest {
    @Test
    public void test() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJyb2xlIjoiMSIsIm5pY2tuYW1lIjoi5ri45a6iIiwic2Vzc2lvbklkIjoiODBhNGU0MjctMTIyYy00YzgxLWExMmYtYWVlZDBiYjE2ODAwIiwiaWQiOiI4IiwiZW1haWwiOiIxMTFAcS5jb20iLCJleHBpcmVkVGltZSI6IjE3Mjg2OTk2NDg3NzMiLCJ1c2VybmFtZSI6IjExMUBxLmNvbSIsImV4cCI6MTczMTI5MTA0OH0.eFi54SDlY6Jk7Lwiord8RYdkvByYOOvoqc41oVZekk8d9qZCWnk2yApwv-ALB5babUpzzKlaSRPPJFcJ_85cCjhqG3Zrkfjs47qugT1s-FMaZ0pNJVvpHefYMoLLGQVRCQDjOh4kixjFsmdHXciWc_U_y8Dq_m9930E9EkI5PaPhY7agLXkPFWB6-n4V6pd3wNvmoPsnM6M8g1benXlm2roW2oOgtzBVtIoh0V3xNxDYOSOrgv3bxiGydVggNpkWVTquzp8c4xOT_SoxywA5bRUhZiIhoRS94R13CHBGYycDNQzAS7VMPV3BBEpYc2cjzlBGUMVaieEFfJC4zI5Gww";
        String email = JwtService.getPayload(jwt, UserLoginDTO.class).getEmail();
        System.out.println(email);
    }
}
