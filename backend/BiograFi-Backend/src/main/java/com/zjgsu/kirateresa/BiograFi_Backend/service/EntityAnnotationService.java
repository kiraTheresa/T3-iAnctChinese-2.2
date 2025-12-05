package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.model.EntityAnnotation;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.AnnotationRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.BatchAnnotationRequest;

import java.util.List;
import java.util.Map;

/**
 * 实体标注服务接口
 */
public interface EntityAnnotationService {

    /**
     * 获取文档的实体标注列表
     * @param documentId 文档ID
     * @return 实体标注列表
     */
    List<EntityAnnotation> getAnnotationsByDocumentId(String documentId);

    /**
     * 获取文档的特定标签实体标注列表
     * @param documentId 文档ID
     * @param label 标签
     * @return 实体标注列表
     */
    List<EntityAnnotation> getAnnotationsByDocumentIdAndLabel(String documentId, String label);

    /**
     * 添加实体标注
     * @param documentId 文档ID
     * @param annotationRequest 标注请求
     * @return 新添加的实体标注
     */
    EntityAnnotation addAnnotation(String documentId, AnnotationRequest annotationRequest);

    /**
     * 批量添加实体标注
     * @param documentId 文档ID
     * @param batchRequest 批量标注请求
     * @return 添加成功的数量
     */
    int addBatchAnnotations(String documentId, BatchAnnotationRequest batchRequest);

    /**
     * 删除实体标注
     * @param documentId 文档ID
     * @param annotationId 标注ID
     */
    void deleteAnnotation(String documentId, Integer annotationId);

    /**
     * 搜索实体标注
     * @param documentId 文档ID
     * @param label 标签（可选）
     * @param text 文本（可选）
     * @return 实体标注列表
     */
    List<EntityAnnotation> searchAnnotations(String documentId, String label, String text);

    /**
     * 根据标签统计实体标注数量
     * @param documentId 文档ID
     * @return 标签统计结果
     */
    Map<String, Long> countAnnotationsByLabel(String documentId);
}
