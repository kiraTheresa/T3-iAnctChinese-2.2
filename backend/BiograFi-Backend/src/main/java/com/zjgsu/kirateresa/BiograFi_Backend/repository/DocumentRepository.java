package com.zjgsu.kirateresa.BiograFi_Backend.repository;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文档数据访问接口
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {

    /**
     * 根据用户ID和项目ID查找文档列表
     * @param userId 用户ID
     * @param projectId 项目ID
     * @return 文档列表
     */
    List<Document> findByUserIdAndProjectId(Integer userId, String projectId);

    /**
     * 根据用户ID查找所有文档
     * @param userId 用户ID
     * @return 文档列表
     */
    List<Document> findByUserId(Integer userId);

    /**
     * 搜索文档
     * @param userId 用户ID
     * @param query 搜索关键词
     * @param projectId 项目ID（可选）
     * @return 文档列表
     */
    @Query(value = "SELECT d FROM Document d WHERE d.userId = :userId " +
            "AND (d.name LIKE %:query% OR d.description LIKE %:query% OR d.content LIKE %:query% OR d.author LIKE %:query%) " +
            "AND (:projectId IS NULL OR d.projectId = :projectId)")
    List<Document> searchDocuments(@Param("userId") Integer userId, 
                                   @Param("query") String query, 
                                   @Param("projectId") String projectId);

}
