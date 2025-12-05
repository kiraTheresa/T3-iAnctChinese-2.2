package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 分词结果项
 */
@Data
public class SegmentToken {
    /**
     * 分词后的词语
     */
    private String text;

    /**
     * 词语在原文中的起始位置
     */
    private Integer start;

    /**
     * 词语在原文中的结束位置
     */
    private Integer end;
}
