package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.model.EntityAnnotation;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.EntityAnnotationRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.AnnotationRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.BatchAnnotationRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.EntityAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 实体标注服务实现类
 */
@Service
public class EntityAnnotationServiceImpl implements EntityAnnotationService {

    @Autowired
    private EntityAnnotationRepository annotationRepository;

    /**
     * 获取文档的实体标注列表
     * @param documentId 文档ID
     * @return 实体标注列表
     */
    @Override
    public List<EntityAnnotation> getAnnotationsByDocumentId(String documentId) {
        return annotationRepository.findByDocumentIdOrderByStartIndexAsc(documentId);
    }

    /**
     * 获取文档的特定标签实体标注列表
     * @param documentId 文档ID
     * @param label 标签
     * @return 实体标注列表
     */
    @Override
    public List<EntityAnnotation> getAnnotationsByDocumentIdAndLabel(String documentId, String label) {
        return annotationRepository.findByDocumentIdAndLabelOrderByStartIndexAsc(documentId, label);
    }

    /**
     * 添加实体标注
     * @param documentId 文档ID
     * @param annotationRequest 标注请求
     * @return 新添加的实体标注
     */
    @Override
    @Transactional
    public EntityAnnotation addAnnotation(String documentId, AnnotationRequest annotationRequest) {
        // 验证必填字段
        if (annotationRequest.getStart() == null || annotationRequest.getEnd() == null || annotationRequest.getLabel() == null || annotationRequest.getLabel().isEmpty()) {
            throw new RuntimeException("缺少必要参数");
        }

        // 创建实体标注
        EntityAnnotation annotation = new EntityAnnotation();
        annotation.setDocumentId(documentId);
        annotation.setStartIndex(annotationRequest.getStart());
        annotation.setEndIndex(annotationRequest.getEnd());
        annotation.setLabel(annotationRequest.getLabel());
        annotation.setTextContent(annotationRequest.getText() != null ? annotationRequest.getText() : "");

        return annotationRepository.save(annotation);
    }

    /**
     * 批量添加实体标注
     * @param documentId 文档ID
     * @param batchRequest 批量标注请求
     * @return 添加成功的数量
     */
    @Override
    @Transactional
    public int addBatchAnnotations(String documentId, BatchAnnotationRequest batchRequest) {
        if (batchRequest == null || batchRequest.getAnnotations() == null || batchRequest.getAnnotations().isEmpty()) {
            return 0;
        }

        List<EntityAnnotation> annotations = new ArrayList<>();
        for (AnnotationRequest request : batchRequest.getAnnotations()) {
            // 验证必填字段
            if (request.getStart() != null && request.getEnd() != null && request.getLabel() != null && !request.getLabel().isEmpty()) {
                EntityAnnotation annotation = new EntityAnnotation();
                annotation.setDocumentId(documentId);
                annotation.setStartIndex(request.getStart());
                annotation.setEndIndex(request.getEnd());
                annotation.setLabel(request.getLabel());
                annotation.setTextContent(request.getText() != null ? request.getText() : "");
                annotations.add(annotation);
            }
        }

        if (annotations.isEmpty()) {
            return 0;
        }

        List<EntityAnnotation> savedAnnotations = annotationRepository.saveAll(annotations);
        return savedAnnotations.size();
    }

    /**
     * 删除实体标注
     * @param documentId 文档ID
     * @param annotationId 标注ID
     */
    @Override
    @Transactional
    public void deleteAnnotation(String documentId, Integer annotationId) {
        // 验证标注是否存在且属于该文档
        EntityAnnotation annotation = annotationRepository.findById(annotationId)
                .orElseThrow(() -> new RuntimeException("标注不存在"));
        
        if (!annotation.getDocumentId().equals(documentId)) {
            throw new RuntimeException("标注不属于该文档");
        }
        
        annotationRepository.delete(annotation);
    }

    /**
     * 搜索实体标注
     * @param documentId 文档ID
     * @param label 标签（可选）
     * @param text 文本（可选）
     * @return 实体标注列表
     */
    @Override
    public List<EntityAnnotation> searchAnnotations(String documentId, String label, String text) {
        return annotationRepository.searchAnnotations(documentId, label, text);
    }

    /**
     * 根据标签统计实体标注数量
     * @param documentId 文档ID
     * @return 标签统计结果
     */
    @Override
    public Map<String, Long> countAnnotationsByLabel(String documentId) {
        List<Map<String, Object>> results = annotationRepository.countByLabel(documentId);
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (String) result.get("label"),
                        result -> ((Number) result.get("count")).longValue()
                ));
    }
}
