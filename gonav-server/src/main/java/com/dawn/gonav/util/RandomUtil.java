package com.dawn.gonav.util;

import java.util.Random;

/**
 * ClassName: RandomUtil
 * Package: com.dawn.dawnblogback.util
 * Description:
 *
 * @Author yrx
 * @Create 2024/2/1 13:50
 * @Version 1.0
 */
public class RandomUtil {
    public static String getEmailCode(){
        Random r = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++){
            int randomNum = r.nextInt(10);
            code.append(randomNum);
        }
        return code.toString();
    }
}
