package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Document;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.DocumentRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.EntityAnnotationRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.DocumentCreateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.DocumentUpdateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 文档服务实现类
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityAnnotationRepository entityAnnotationRepository;

    /**
     * 根据用户ID获取文档列表
     * @param userId 用户ID
     * @param projectId 项目ID（可选）
     * @return 文档列表
     */
    @Override
    public List<Document> getDocumentsByUserId(Integer userId, String projectId) {
        if (projectId != null && !projectId.isEmpty()) {
            return documentRepository.findByUserIdAndProjectId(userId, projectId);
        } else {
            return documentRepository.findByUserId(userId);
        }
    }

    /**
     * 根据文档ID获取文档详情
     * @param documentId 文档ID
     * @return 文档详情
     */
    @Override
    public Optional<Document> getDocumentById(String documentId) {
        return documentRepository.findById(documentId);
    }

    /**
     * 创建文档
     * @param createRequest 文档创建请求
     * @return 新创建的文档
     */
    @Override
    @Transactional
    public Document createDocument(DocumentCreateRequest createRequest) {
        // 验证必填字段
        if (createRequest.getUserId() == null || createRequest.getProjectId() == null || createRequest.getName() == null || createRequest.getName().isEmpty()) {
            throw new RuntimeException("缺少必要参数: userId, projectId, name 都是必需的");
        }

        // 生成文档ID
        String documentId = System.currentTimeMillis() + "" + UUID.randomUUID().toString().replace("-", "").substring(0, 9);

        // 创建文档
        Document document = new Document();
        document.setId(documentId);
        document.setUserId(createRequest.getUserId());
        document.setProjectId(createRequest.getProjectId());
        document.setName(createRequest.getName());
        document.setDescription(createRequest.getDescription() != null ? createRequest.getDescription() : "");
        document.setContent(createRequest.getContent() != null ? createRequest.getContent() : "");
        document.setAuthor(createRequest.getAuthor() != null ? createRequest.getAuthor() : "");

        return documentRepository.save(document);
    }

    /**
     * 更新文档
     * @param documentId 文档ID
     * @param updateRequest 文档更新请求
     * @return 更新后的文档
     */
    @Override
    @Transactional
    public Document updateDocument(String documentId, DocumentUpdateRequest updateRequest) {
        // 验证文档是否存在
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (!optionalDocument.isPresent()) {
            throw new RuntimeException("文档不存在");
        }

        Document document = optionalDocument.get();

        // 更新文档信息
        if (updateRequest.getName() != null) {
            document.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            document.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getContent() != null) {
            document.setContent(updateRequest.getContent());
        }
        if (updateRequest.getAuthor() != null) {
            document.setAuthor(updateRequest.getAuthor());
        }

        return documentRepository.save(document);
    }

    /**
     * 删除文档
     * @param documentId 文档ID
     */
    @Override
    @Transactional
    public void deleteDocument(String documentId) {
        // 验证文档是否存在
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (!optionalDocument.isPresent()) {
            throw new RuntimeException("文档不存在");
        }

        // 删除文档的所有实体标注
        entityAnnotationRepository.deleteByDocumentId(documentId);

        // 删除文档
        documentRepository.deleteById(documentId);
    }

    /**
     * 搜索文档
     * @param userId 用户ID
     * @param query 搜索关键词
     * @param projectId 项目ID（可选）
     * @return 搜索结果
     */
    @Override
    public List<Document> searchDocuments(Integer userId, String query, String projectId) {
        if (query == null || query.isEmpty()) {
            return getDocumentsByUserId(userId, projectId);
        }
        return documentRepository.searchDocuments(userId, query, projectId);
    }
}
