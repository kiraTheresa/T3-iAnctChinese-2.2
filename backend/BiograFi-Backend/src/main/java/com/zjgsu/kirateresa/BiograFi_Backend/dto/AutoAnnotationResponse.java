package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

import java.util.List;

/**
 * 自动标注响应
 */
@Data
public class AutoAnnotationResponse {
    /**
     * 自动标注结果列表
     */
    private List<AutoAnnotationItem> annotations;
}
