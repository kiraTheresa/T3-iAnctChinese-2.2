package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.AutoAnnotationItem;

import java.util.List;

/**
 * AI 服务接口，定义 AI 相关的服务方法
 */
public interface AiService {

    /**
     * 文本分析
     * @param text 要分析的文本
     * @param model 可选的模型名称
     * @return 分析结果
     */
    String analyzeText(String text, String model);

    /**
     * 问答系统
     * @param text 原文
     * @param question 问题
     * @param model 可选的模型名称
     * @return 回答
     */
    String qaText(String text, String question, String model);

    /**
     * 自动标注
     * @param text 要标注的文本
     * @return 标注结果列表
     */
    List<AutoAnnotationItem> autoAnnotate(String text);
}
