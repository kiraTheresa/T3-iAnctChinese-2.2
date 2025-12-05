package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 可视化总览统计DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisualizationOverview {
    /**
     * 总字符数
     */
    private Integer totalChars;

    /**
     * 各标签数量统计
     */
    private Map<String, Long> labelCounts;
}
