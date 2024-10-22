package com.dawn.gonav.authentication.config;

import com.dawn.gonav.authentication.handler.exception.CustomAccessDeniedHandler;
import com.dawn.gonav.authentication.handler.exception.CustomAuthenticationExceptionHandler;
import com.dawn.gonav.authentication.handler.exception.CustomSecurityExceptionHandler;
import com.dawn.gonav.authentication.handler.login.LoginFailHandler;
import com.dawn.gonav.authentication.handler.login.LoginSuccessHandler;
import com.dawn.gonav.authentication.handler.login.email.EmailAuthenticationFilter;
import com.dawn.gonav.authentication.handler.login.email.EmailAuthenticationProvider;
import com.dawn.gonav.authentication.handler.login.github.GitHubAuthenticationFilter;
import com.dawn.gonav.authentication.handler.login.github.GitHubAuthenticationProvider;
import com.dawn.gonav.authentication.handler.login.linuxDo.LinuxDoAuthenticationFilter;
import com.dawn.gonav.authentication.handler.login.linuxDo.LinuxDoAuthenticationProvider;
import com.dawn.gonav.authentication.handler.login.username.UsernameAuthenticationFilter;
import com.dawn.gonav.authentication.handler.login.username.UsernameAuthenticationProvider;
import com.dawn.gonav.authentication.resourceApi.adminApi.AdminJwtAuthenticationFilter;
import com.dawn.gonav.authentication.resourceApi.publicApi.PublicApiAuthenticationFilter;
import com.dawn.gonav.authentication.resourceApi.userApi.UserJwtAuthenticationFilter;
import com.dawn.gonav.user.service.JwtService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ApplicationContext applicationContext;
    private final CustomAuthenticationExceptionHandler customAuthenticationExceptionHandler;
    private final AuthenticationEntryPoint authenticationExceptionHandler = new CustomAuthenticationExceptionHandler();
    private final Filter globalSpringSecurityExceptionHandler = new CustomSecurityExceptionHandler();
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig(
            ApplicationContext applicationContext,
            CustomAuthenticationExceptionHandler customAuthenticationExceptionHandler,
            CustomAccessDeniedHandler customAccessDeniedHandler
    ){
        this.applicationContext = applicationContext;
        this.customAuthenticationExceptionHandler = customAuthenticationExceptionHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private void commonHttpSetting(HttpSecurity http) throws Exception {
        // 禁用SpringSecurity默认filter。这些filter都是非前后端分离项目的产物，用不上.
        // yml配置文件将日志设置DEBUG模式，就能看到加载了哪些filter
        // logging:
        //    level:
        //       org.springframework.security: DEBUG
        // 表单登录/登出、session管理、csrf防护等默认配置，如果不disable。会默认创建默认filter
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // requestCache用于重定向，前后端分析项目无需重定向，requestCache也用不上
                .requestCache(cache -> cache
                        .requestCache(new NullRequestCache())
                )
                // 无需给用户一个匿名身份
                .anonymous(AbstractHttpConfigurer::disable);
        // 处理 SpringSecurity 异常响应结果。响应数据的结构，改成业务统一的JSON结构。不要框架默认的响应结构
        http.exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                // 认证失败异常
                                .authenticationEntryPoint(authenticationExceptionHandler)
                // 鉴权失败异常
//                        .accessDeniedHandler(authorizationExceptionHandler)
        );
        // 其他未知异常. 尽量提前加载。
        http.addFilterBefore(globalSpringSecurityExceptionHandler, SecurityContextHolderFilter.class);

    }
    @Bean
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        commonHttpSetting(http);
        // 使用securityMatcher限定当前配置作用的路径
        http.securityMatcher("/public/user/login/*")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        LoginSuccessHandler loginSuccessHandler = applicationContext.getBean(LoginSuccessHandler.class);
        LoginFailHandler loginFailHandler = applicationContext.getBean(LoginFailHandler.class);

        // 加一个登录方式。用户名/邮箱、密码登录
        String usernameLoginPath = "/public/user/login/password";
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                // @PostMapping("/user/login/username")
                new AntPathRequestMatcher(usernameLoginPath, HttpMethod.POST.name()),
                // 校验
                new ProviderManager(List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                // 成功失败处理
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 加一个登录方式。邮箱、验证码登录
        String emailLoginPath = "/public/user/login/code";
        EmailAuthenticationFilter emailLoginFilter = new EmailAuthenticationFilter(
                new AntPathRequestMatcher(emailLoginPath, HttpMethod.POST.name()),
                new ProviderManager(List.of(applicationContext.getBean(EmailAuthenticationProvider.class))),
                // 成功失败处理
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(emailLoginFilter, UsernamePasswordAuthenticationFilter.class);

        // 加一个登录方式。linuxdo 登录
        LinuxDoAuthenticationFilter linuxdoFilter = new LinuxDoAuthenticationFilter(
                new AntPathRequestMatcher("/public/user/login/linuxdo", HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(applicationContext.getBean(LinuxDoAuthenticationProvider.class))),
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(linuxdoFilter, UsernamePasswordAuthenticationFilter.class);

        // 加一个登录方式。github 登录
        GitHubAuthenticationFilter githubFilter = new GitHubAuthenticationFilter(
                new AntPathRequestMatcher("/public/user/login/github", HttpMethod.POST.name()),
                new ProviderManager(
                        List.of(applicationContext.getBean(GitHubAuthenticationProvider.class))),
                loginSuccessHandler,
                loginFailHandler);
        http.addFilterBefore(githubFilter, UsernamePasswordAuthenticationFilter.class);

        // 异常处理
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationExceptionHandler)
                        .accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    /**
     * 需要鉴权USER的过滤器链
     */
    @Bean
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        commonHttpSetting(http);
        // 使用securityMatcher限定当前配置作用的路径
        http.securityMatcher("/user/**");
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        // JWT Filter
        UserJwtAuthenticationFilter userJwtAuthenticationFilter = new UserJwtAuthenticationFilter(applicationContext.getBean(JwtService.class));
        http.addFilterBefore(userJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        commonHttpSetting(http);
        // 使用securityMatcher限定当前配置作用的路径
        http.securityMatcher("/admin/**");
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        // JWT Filter
        AdminJwtAuthenticationFilter userJwtAuthenticationFilter = new AdminJwtAuthenticationFilter(applicationContext.getBean(JwtService.class));
        http.addFilterBefore(userJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /** 其余路径，走这个默认过滤链 */
    @Bean
    @Order(Integer.MAX_VALUE) // 这个过滤链最后加载
    public SecurityFilterChain defaultApiFilterChain(HttpSecurity http) throws Exception {
        commonHttpSetting(http);
        http // 不用securityMatcher表示缺省值，匹配不上其他过滤链时，都走这个过滤链
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.addFilterBefore(new PublicApiAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
