package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 项目更新请求DTO
 */
@Data
public class ProjectUpdateRequest {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;
}
