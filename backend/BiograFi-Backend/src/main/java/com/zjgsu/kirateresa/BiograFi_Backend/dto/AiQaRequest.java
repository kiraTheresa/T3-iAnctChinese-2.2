package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * AI 问答请求
 */
@Data
public class AiQaRequest {
    /**
     * 原文内容
     */
    private String text;

    /**
     * 问题
     */
    private String question;

    /**
     * 可选的模型名称，默认使用配置中的模型
     */
    private String model;
}
