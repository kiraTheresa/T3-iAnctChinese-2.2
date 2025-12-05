package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 文档更新请求DTO
 */
@Data
public class DocumentUpdateRequest {
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
