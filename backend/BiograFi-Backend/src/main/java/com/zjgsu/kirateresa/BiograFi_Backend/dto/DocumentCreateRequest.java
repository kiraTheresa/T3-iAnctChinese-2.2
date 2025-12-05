package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 文档创建请求DTO
 */
@Data
public class DocumentCreateRequest {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 文档内容
     */
    private String content;

    /**
     * 作者
     */
    private String author;
}
