package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.model.EntityAnnotation;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.AnnotationRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.BatchAnnotationRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.EntityAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 实体标注Controller
 */
@RestController
@RequestMapping("/api")
public class EntityAnnotationController {

    @Autowired
    private EntityAnnotationService annotationService;

    /**
     * 获取文档的实体标注列表
     * @param documentId 文档ID
     * @return 实体标注列表
     */
    @GetMapping("/documents/{documentId}/annotations")
    public ResponseEntity<ApiResponse<List<EntityAnnotation>>> getAnnotations(@PathVariable String documentId) {
        try {
            List<EntityAnnotation> annotations = annotationService.getAnnotationsByDocumentId(documentId);
            return ResponseEntity.ok(ApiResponse.success(annotations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 添加实体标注
     * @param documentId 文档ID
     * @param annotationRequest 标注请求
     * @return 新添加的实体标注
     */
    @PostMapping("/documents/{documentId}/annotations/entity")
    public ResponseEntity<ApiResponse<EntityAnnotation>> addAnnotation(
            @PathVariable String documentId,
            @RequestBody AnnotationRequest annotationRequest) {
        try {
            EntityAnnotation annotation = annotationService.addAnnotation(documentId, annotationRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(annotation));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 批量添加实体标注
     * @param documentId 文档ID
     * @param batchRequest 批量标注请求
     * @return 添加成功的数量
     */
    @PostMapping("/documents/{documentId}/annotations/entity/bulk")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> addBatchAnnotations(
            @PathVariable String documentId,
            @RequestBody BatchAnnotationRequest batchRequest) {
        try {
            int insertedCount = annotationService.addBatchAnnotations(documentId, batchRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(Map.of("insertedCount", insertedCount)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 删除实体标注
     * @param documentId 文档ID
     * @param annotationId 标注ID
     * @return 删除结果
     */
    @DeleteMapping("/documents/{documentId}/annotations/entity/{annotationId}")
    public ResponseEntity<ApiResponse<Void>> deleteAnnotation(
            @PathVariable String documentId,
            @PathVariable Integer annotationId) {
        try {
            annotationService.deleteAnnotation(documentId, annotationId);
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
     * 搜索实体标注
     * @param documentId 文档ID
     * @param label 标签（可选）
     * @param text 文本（可选）
     * @return 搜索结果
     */
    @GetMapping("/annotations/search")
    public ResponseEntity<ApiResponse<List<EntityAnnotation>>> searchAnnotations(
            @RequestParam("documentId") String documentId,
            @RequestParam(value = "label", required = false) String label,
            @RequestParam(value = "text", required = false) String text) {
        try {
            List<EntityAnnotation> annotations = annotationService.searchAnnotations(documentId, label, text);
            return ResponseEntity.ok(ApiResponse.success(annotations));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 根据标签统计实体标注数量
     * @param documentId 文档ID
     * @return 标签统计结果
     */
    @GetMapping("/documents/{documentId}/annotations/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> countAnnotationsByLabel(@PathVariable String documentId) {
        try {
            Map<String, Long> countResult = annotationService.countAnnotationsByLabel(documentId);
            return ResponseEntity.ok(ApiResponse.success(countResult));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }
}
