package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Document;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.DocumentCreateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.DocumentUpdateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文档Controller
 */
@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    /**
     * 获取用户文档列表
     * @param userId 用户ID
     * @param projectId 项目ID（可选）
     * @return 文档列表
     */
    @GetMapping("/documents")
    public ResponseEntity<ApiResponse<List<Document>>> getDocuments(
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "projectId", required = false) String projectId) {
        try {
            List<Document> documents = documentService.getDocumentsByUserId(userId, projectId);
            return ResponseEntity.ok(ApiResponse.success(documents));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 获取文档详情
     * @param documentId 文档ID
     * @return 文档详情
     */
    @GetMapping("/documents/{documentId}")
    public ResponseEntity<ApiResponse<Document>> getDocumentById(@PathVariable String documentId) {
        try {
            return documentService.getDocumentById(documentId)
                    .map(document -> ResponseEntity.ok(ApiResponse.success(document)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ApiResponse.error("文档不存在")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 创建文档
     * @param createRequest 文档创建请求
     * @return 新创建的文档
     */
    @PostMapping("/documents")
    public ResponseEntity<ApiResponse<Document>> createDocument(@RequestBody DocumentCreateRequest createRequest) {
        try {
            Document document = documentService.createDocument(createRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(document));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 更新文档
     * @param documentId 文档ID
     * @param updateRequest 文档更新请求
     * @return 更新后的文档
     */
    @PutMapping("/documents/{documentId}")
    public ResponseEntity<ApiResponse<Document>> updateDocument(
            @PathVariable String documentId,
            @RequestBody DocumentUpdateRequest updateRequest) {
        try {
            Document document = documentService.updateDocument(documentId, updateRequest);
            return ResponseEntity.ok(ApiResponse.success(document));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 删除文档
     * @param documentId 文档ID
     * @return 删除结果
     */
    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable String documentId) {
        try {
            documentService.deleteDocument(documentId);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 搜索文档
     * @param userId 用户ID
     * @param query 搜索关键词
     * @param projectId 项目ID（可选）
     * @return 搜索结果
     */
    @GetMapping("/documents/search")
    public ResponseEntity<ApiResponse<List<Document>>> searchDocuments(
            @RequestParam("userId") Integer userId,
            @RequestParam("query") String query,
            @RequestParam(value = "projectId", required = false) String projectId) {
        try {
            List<Document> documents = documentService.searchDocuments(userId, query, projectId);
            return ResponseEntity.ok(ApiResponse.success(documents));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }
}
