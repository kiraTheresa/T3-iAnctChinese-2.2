package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * AI 文本分析请求
 */
@Data
public class AiAnalyzeRequest {
    /**
     * 要分析的文本内容
     */
    private String text;

    /**
     * 可选的模型名称，默认使用配置中的模型
     */
    private String model;
}
