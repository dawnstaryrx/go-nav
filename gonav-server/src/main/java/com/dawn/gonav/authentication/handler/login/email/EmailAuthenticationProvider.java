package com.dawn.gonav.authentication.handler.login.email;

import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.dawn.gonav.model.constant.RedisConstant.EMAIL_LOGIN_CODE_PREFIX;

/**
 * 帐号密码登录认证
 */
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Autowired
  private StringRedisTemplate stringRedisTemplate;


  public EmailAuthenticationProvider() {
    super();
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 用户提交的邮箱 + 验证码：
    String email = (String)authentication.getPrincipal();
    String code = (String) authentication.getCredentials();

    // 查数据库，匹配用户信息
    User user = userService.getUserByEmail(email);
    // 比较验证码是否正确
    ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
    String key = EMAIL_LOGIN_CODE_PREFIX + email;
    String redisCode = operations.get(key);
    if (user == null || !code.equals(redisCode)) {
      throw new BadCredentialsException("邮箱或验证码不正确");
    }
    EmailAuthentication token = new EmailAuthentication();
    token.setCurrentUser(JSONUtil.convert(user, UserLoginDTO.class));
    token.setAuthenticated(true); // 认证通过，这里一定要设成true
    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(EmailAuthentication.class);
  }
}

