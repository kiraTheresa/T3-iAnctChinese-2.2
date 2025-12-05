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
                // 开发环境下允许所有请求访问
                .anyRequest().permitAll()
            )
            // 禁用表单登录
            .formLogin(form -> form.disable())
            // 禁用HTTP Basic认证
            .httpBasic(httpBasic -> httpBasic.disable());
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}