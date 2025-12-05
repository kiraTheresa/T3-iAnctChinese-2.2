package com.zjgsu.kirateresa.BiograFi_Backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 开发环境下禁用CSRF保护
            .authorizeHttpRequests(authorize -> authorize
                // 允许公开访问的端点
                .requestMatchers("/api/health").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/api/users/login", "/api/users/register").permitAll() // 允许登录和注册端点公开访问
                // 其他API端点需要认证
                .requestMatchers("/api/**").authenticated()
                // 其他所有请求允许访问
                .anyRequest().permitAll()
            )
            // 配置表单登录（如果需要）
            .formLogin(form -> form.disable())
            // 配置HTTP Basic认证（如果需要）
            .httpBasic(httpBasic -> httpBasic.disable());
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}