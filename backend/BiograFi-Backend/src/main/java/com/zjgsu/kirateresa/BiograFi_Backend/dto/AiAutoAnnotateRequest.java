package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * AI 自动标注请求
 */
@Data
public class AiAutoAnnotateRequest {
    /**
     * 要标注的文本内容
     */
    private String text;
}
