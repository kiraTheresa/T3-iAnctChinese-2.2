package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Document;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.DocumentCreateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.DocumentUpdateRequest;

import java.util.List;
import java.util.Optional;

/**
 * 文档服务接口
 */
public interface DocumentService {

    /**
     * 根据用户ID获取文档列表
     * @param userId 用户ID
     * @param projectId 项目ID（可选）
     * @return 文档列表
     */
    List<Document> getDocumentsByUserId(Integer userId, String projectId);

    /**
     * 根据文档ID获取文档详情
     * @param documentId 文档ID
     * @return 文档详情
     */
    Optional<Document> getDocumentById(String documentId);

    /**
     * 创建文档
     * @param createRequest 文档创建请求
     * @return 新创建的文档
     */
    Document createDocument(DocumentCreateRequest createRequest);

    /**
     * 更新文档
     * @param documentId 文档ID
     * @param updateRequest 文档更新请求
     * @return 更新后的文档
     */
    Document updateDocument(String documentId, DocumentUpdateRequest updateRequest);

    /**
     * 删除文档
     * @param documentId 文档ID
     */
    void deleteDocument(String documentId);

    /**
     * 搜索文档
     * @param userId 用户ID
     * @param query 搜索关键词
     * @param projectId 项目ID（可选）
     * @return 搜索结果
     */
    List<Document> searchDocuments(Integer userId, String query, String projectId);
}
