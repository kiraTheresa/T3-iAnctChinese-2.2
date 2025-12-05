package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

import java.util.List;

/**
 * 导出文档请求DTO
 */
@Data
public class ExportRequest {
    /**
     * 文档ID列表
     */
    private List<String> documentIds;

    /**
     * 导出格式，支持 txt+csv 或 json
     */
    private String exportFormat = "txt+csv";
}
