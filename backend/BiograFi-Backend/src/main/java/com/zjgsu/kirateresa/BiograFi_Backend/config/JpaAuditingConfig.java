package com.zjgsu.kirateresa.BiograFi_Backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA审计配置
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    // 启用JPA审计功能，支持@CreatedDate和@LastModifiedDate注解
}
