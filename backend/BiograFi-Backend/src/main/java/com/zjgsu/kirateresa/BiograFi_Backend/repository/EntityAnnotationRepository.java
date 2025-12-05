package com.zjgsu.kirateresa.BiograFi_Backend.repository;

import com.zjgsu.kirateresa.BiograFi_Backend.model.EntityAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 实体标注数据访问接口
 */
@Repository
public interface EntityAnnotationRepository extends JpaRepository<EntityAnnotation, Integer> {

    /**
     * 根据文档ID查找所有实体标注
     * @param documentId 文档ID
     * @return 实体标注列表
     */
    List<EntityAnnotation> findByDocumentIdOrderByStartIndexAsc(String documentId);

    /**
     * 根据文档ID和标签查找实体标注
     * @param documentId 文档ID
     * @param label 标签
     * @return 实体标注列表
     */
    List<EntityAnnotation> findByDocumentIdAndLabelOrderByStartIndexAsc(String documentId, String label);

    /**
     * 搜索实体标注
     * @param documentId 文档ID
     * @param label 标签（可选）
     * @param text 文本（可选）
     * @return 实体标注列表
     */
    @Query(value = "SELECT a FROM EntityAnnotation a WHERE a.documentId = :documentId " +
            "AND (:label IS NULL OR a.label = :label) " +
            "AND (:text IS NULL OR a.textContent LIKE %:text%) " +
            "ORDER BY a.startIndex ASC")
    List<EntityAnnotation> searchAnnotations(@Param("documentId") String documentId,
                                             @Param("label") String label,
                                             @Param("text") String text);

    /**
     * 根据文档ID和标签统计数量
     * @param documentId 文档ID
     * @return 标签统计结果
     */
    @Query(value = "SELECT a.label, COUNT(a) FROM EntityAnnotation a WHERE a.documentId = :documentId GROUP BY a.label")
    List<Map<String, Object>> countByLabel(@Param("documentId") String documentId);

    /**
     * 删除文档的所有实体标注
     * @param documentId 文档ID
     */
    void deleteByDocumentId(String documentId);

    /**
     * 统计文档中特定标签的数量
     * @param documentId 文档ID
     * @param label 标签
     * @return 数量
     */
    long countByDocumentIdAndLabel(String documentId, String label);

    /**
     * 获取文档中所有地名实体
     * @param documentId 文档ID
     * @return 地名实体列表
     */
    List<EntityAnnotation> findByDocumentIdAndLabel(String documentId, String label);

}
