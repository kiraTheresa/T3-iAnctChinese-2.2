package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量实体标注请求DTO
 */
@Data
public class BatchAnnotationRequest {
    /**
     * 标注列表
     */
    private List<AnnotationRequest> annotations;
}
