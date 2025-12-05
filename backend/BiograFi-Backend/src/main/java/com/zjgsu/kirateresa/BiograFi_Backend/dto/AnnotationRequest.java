package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 实体标注请求DTO
 */
@Data
public class AnnotationRequest {
    /**
     * 开始位置
     */
    private Integer start;

    /**
     * 结束位置
     */
    private Integer end;

    /**
     * 标签
     */
    private String label;

    /**
     * 文本内容
     */
    private String text;
}
