package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.VisualizationOverview;

import java.util.Map;
import java.util.List;

/**
 * 可视化分析服务接口
 */
public interface VisualizationService {

    /**
     * 获取文档可视化总览统计
     * @param documentId 文档ID
     * @return 可视化总览统计
     */
    VisualizationOverview getVisualizationOverview(String documentId);

    /**
     * 获取地点可视化数据
     * @param documentId 文档ID
     * @return 地点可视化数据
     */
    Map<String, Object> getLocationsVisualization(String documentId);

    /**
     * 获取人物关系图数据
     * @param documentId 文档ID
     * @return 人物关系图数据
     */
    Map<String, Object> getRelationshipsVisualization(String documentId);

    /**
     * 获取时间轴数据
     * @param documentId 文档ID
     * @return 时间轴数据
     */
    Map<String, Object> getTimelineVisualization(String documentId);
}
