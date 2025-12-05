package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 自动标注结果项
 */
@Data
public class AutoAnnotationItem {
    /**
     * 实体在原文中的起始位置
     */
    private Integer start;

    /**
     * 实体在原文中的结束位置
     */
    private Integer end;

    /**
     * 实体标签，必须是以下之一：人物、地名、时间、器物、概念
     */
    private String label;

    /**
     * 实体文本内容
     */
    private String text;
}
