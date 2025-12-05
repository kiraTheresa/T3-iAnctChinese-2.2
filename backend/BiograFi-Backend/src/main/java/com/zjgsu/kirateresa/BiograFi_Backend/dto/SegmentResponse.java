package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

import java.util.List;

/**
 * 分词响应
 */
@Data
public class SegmentResponse {
    /**
     * 分词结果列表
     */
    private List<SegmentToken> tokens;
}
