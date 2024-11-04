package com.dawn.gonav.user.service.impl;

import com.dawn.gonav.model.dto.UserInfoDTO;
import com.dawn.gonav.model.dto.UserLoginDTO;
import com.dawn.gonav.user.mapper.UserMapper;
import com.dawn.gonav.user.service.EmailService;
import com.dawn.gonav.user.service.UserService;
import com.dawn.gonav.exception.ExceptionTool;
import com.dawn.gonav.model.dto.EmailDTO;
import com.dawn.gonav.model.dto.RegisterDTO;
import com.dawn.gonav.model.enums.CodeTypeEnum;
import com.dawn.gonav.model.enums.UserTypeEnum;
import com.dawn.gonav.model.po.User;
import com.dawn.gonav.model.properties.AdminProperties;
import com.dawn.gonav.util.CurrentUserUtil;
import com.dawn.gonav.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.dawn.gonav.model.constant.RedisConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;
    @Autowired
    private AdminProperties adminProperties;
    @Override
    public void sendCode(String email, Integer type) {
        User userByEmail = userMapper.findUserByEmail(email);
        if (email == null){
            ExceptionTool.throwException("邮箱不能为空！");
        }
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail(email);
        String code = RandomUtil.getEmailCode();
        String content = null;
        String key = null;
        if (CodeTypeEnum.REGISTER_CODE.getType().equals(type)){
            // 判断是否已经注册
            if (userByEmail != null){
                ExceptionTool.throwException("用户已存在！");
            }
            // 注册邮件
            emailDTO.setTitle("Go导航——注册");
            content = "注册验证码为 " + code + " ，五分钟有效，请妥善保管！";
            key = EMAIL_REGISTER_CODE_PREFIX + emailDTO.getEmail();
        } else if(CodeTypeEnum.LOGIN_CODE.getType().equals(type)){
            if (userByEmail == null){
                ExceptionTool.throwException("用户不存在，请注册！");
            }
            // 登录邮件
            emailDTO.setTitle("Go导航——登录");
            content = "登录验证码为 " + code + " ，五分钟有效，请妥善保管！";
            key = EMAIL_LOGIN_CODE_PREFIX + emailDTO.getEmail();
        } else if (CodeTypeEnum.RESET_CODE.getType().equals(type)){
            if (userByEmail == null){
                ExceptionTool.throwException("用户不存在，请注册！");
            }
            // 重置密码邮件
            emailDTO.setTitle("Go导航——重置密码");
            content = "重置密码的验证码为 " + code + " ，五分钟有效，请妥善保管！";
            key = EMAIL_RESET_CODE_PREFIX + emailDTO.getEmail();
        } else {
            ExceptionTool.throwException("未知的验证码类型");
        }
        emailDTO.setContent(content);
        // 往Redis中存储一个键值对
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(key,  code, EMAIL_CODE_TIME_SECOND, TimeUnit.SECONDS);
        // TODO 模拟发送邮件
        emailService.sendMsg(emailDTO);
        log.info("发送邮件成功" + emailDTO.getContent());
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        // 检查两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getRePassword())){
            ExceptionTool.throwException("两次密码不一致！");
        }
        // 检查验证码是否正确
        // 从Redis中获取出验证码
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String key = EMAIL_REGISTER_CODE_PREFIX + registerDTO.getEmail();
        String redisCode = operations.get(key);
        if (!registerDTO.getCode().equals(redisCode)){
            ExceptionTool.throwException("验证码错误！");
        }
        // 根据邮箱查询用户是否存在
        User userByEmail = userMapper.findUserByEmail(registerDTO.getEmail());
        if (userByEmail != null){
            ExceptionTool.throwException("用户已存在！");
        }
        List<String> adminEmails = adminProperties.getEmail();
        User user = new User();
        user.setUsername(registerDTO.getEmail());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        if (adminEmails.contains(registerDTO.getEmail())){
            user.setRole(UserTypeEnum.ADMIN.getCode());
        } else {
            user.setRole(UserTypeEnum.USER.getCode());
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.add(user);
        // 删除Redis中的验证码
        operations.getOperations().delete(key);
    }

    @Override
    public void resetPwd(RegisterDTO registerDTO) {
        // 检查两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getRePassword())){
            ExceptionTool.throwException("两次密码不一致");
        }
        // 检查验证码是否正确
        // 从Redis中获取出验证码
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String key = EMAIL_RESET_CODE_PREFIX + registerDTO.getEmail();
        String redisCode = operations.get(key);
        if (!registerDTO.getCode().equals(redisCode)){
            ExceptionTool.throwException("验证码错误！");
        }
        // 根据邮箱查询用户是否存在
        User userByEmail = userMapper.findUserByEmail(registerDTO.getEmail());
        if (userByEmail == null){
            ExceptionTool.throwException("用户不存在，请注册");
        }
        userByEmail.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userByEmail.setUpdateTime(LocalDateTime.now());
        userMapper.update(userByEmail);
        // 删除Redis中的验证码
        operations.getOperations().delete(key);
    }

    @Override
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        User userByEmail = userMapper.findUserByEmail(usernameOrEmail);
        if (userByEmail != null){
            return userByEmail;
        }
        User userByUsername = userMapper.findUserByUsername(usernameOrEmail);
        if (userByUsername != null){
            return userByUsername;
        }
        ExceptionTool.throwException("用户不存在");
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        User userByEmail = userMapper.findUserByEmail(email);
        if (userByEmail != null){
            return userByEmail;
        }
        ExceptionTool.throwException("用户不存在");
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        User userByUsername = userMapper.findUserByUsername(username);
        if (userByUsername != null){
            return userByUsername;
        }
        ExceptionTool.throwException("用户不存在");
        return null;
    }

    @Override
    public User getUserByOpenId(String openId, String platform) {
        User user = new User();
        if ("LinuxDo".equals(platform)){
            user = userMapper.findUserByLinuxDoOpenId(openId);
        } else if ("GitHub".equals(platform)) {
            user = userMapper.findUserByGitHubOpenId(openId);
        } else {
            ExceptionTool.throwException("未知的第三方平台");
        }
        return user;
    }

    @Override
    public void createUserWithOpenId(User user, String openId, String platform) {
        User userByOpenId = new User();
        if ("LinuxDo".equals(platform)){
            userByOpenId.setRole(1);
            userByOpenId.setLinuxdoOpenid(openId);
            userByOpenId.setCreateTime(LocalDateTime.now());
            userByOpenId.setUpdateTime(LocalDateTime.now());
        } else if ("GitHub".equals(platform)) {
            userByOpenId.setRole(1);
            userByOpenId.setGithubOpenid(openId);
            userByOpenId.setCreateTime(LocalDateTime.now());
            userByOpenId.setUpdateTime(LocalDateTime.now());
        } else {
            ExceptionTool.throwException("未知的第三方平台");
        }
        userMapper.add(userByOpenId);
    }

    @Override
    public void updateUser(UserInfoDTO userInfoDTO) {
        UserLoginDTO curr = CurrentUserUtil.getCurrentUser();
        if (!curr.getId().equals(userInfoDTO.getId())){
            ExceptionTool.throwException("用户信息错误");
        }
        User user = userMapper.findUserById(userInfoDTO.getId());
        user.setNickname(userInfoDTO.getNickname());
        user.setPhone(userInfoDTO.getPhone());
        user.setEmail(userInfoDTO.getEmail());
        user.setUpdateTime(LocalDateTime.now());
        user.setUsername(userInfoDTO.getUsername());
        user.setGithubOpenid(userInfoDTO.getGithubOpenid());
        user.setLinuxdoOpenid(userInfoDTO.getLinuxdoOpenid());
        userMapper.update(user);
    }
}
