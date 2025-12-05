package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 文本分词请求
 */
@Data
public class SegmentRequest {
    /**
     * 要分词的文本内容
     */
    private String text;
}
