package com.dawn.gonav.authentication.config;

import com.dawn.gonav.authentication.resourceApi.publicApi.PublicApiAuthenticationFilter;
import com.dawn.gonav.authentication.resourceApi.userApi.UserJwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
    }

    /**
     * 需要鉴权USER的过滤器链
     */
    @Bean
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        commonHttpSetting(http);
        // 使用securityMatcher限定当前配置作用的路径
        http.securityMatcher("/private/**");
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        // JWT Filter
        UserJwtAuthenticationFilter userJwtAuthenticationFilter = new UserJwtAuthenticationFilter();
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
