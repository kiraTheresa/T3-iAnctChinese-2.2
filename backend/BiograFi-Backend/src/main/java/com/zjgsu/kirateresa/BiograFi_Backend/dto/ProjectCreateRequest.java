package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 项目创建请求DTO
 */
@Data
public class ProjectCreateRequest {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;
}
