package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.SegmentToken;

import java.util.List;

/**
 * 分词服务接口，定义分词相关的服务方法
 */
public interface SegmentService {

    /**
     * 文本分词
     * @param text 要分词的文本
     * @return 分词结果列表
     */
    List<SegmentToken> segmentText(String text);
}
