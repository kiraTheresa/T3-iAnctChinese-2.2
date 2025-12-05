package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Document;
import com.zjgsu.kirateresa.BiograFi_Backend.model.EntityAnnotation;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.DocumentRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.EntityAnnotationRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ExportRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导出服务实现类
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityAnnotationRepository annotationRepository;

    /**
     * 导出文档及标注
     * @param exportRequest 导出请求
     * @return 导出结果
     */
    @Override
    public Map<String, Object> exportDocuments(ExportRequest exportRequest) {
        // 验证必填字段
        if (exportRequest.getDocumentIds() == null || exportRequest.getDocumentIds().isEmpty()) {
            throw new RuntimeException("请提供要导出的文档ID列表");
        }

        // 获取文档列表
        List<Document> documents = documentRepository.findAllById(exportRequest.getDocumentIds());
        if (documents.isEmpty()) {
            throw new RuntimeException("未找到指定的文档");
        }

        // 导出时间
        String exportTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 构建导出结果
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("exportId", "exp_" + System.currentTimeMillis());
        result.put("exportCount", documents.size());

        // 构建导出文件信息（简化实现，实际应该生成真实文件）
        List<Map<String, String>> exportedFiles = new ArrayList<>();
        for (Document doc : documents) {
            // 获取文档的实体标注
            List<EntityAnnotation> annotations = annotationRepository.findByDocumentIdOrderByStartIndexAsc(doc.getId());

            // 构建导出文件信息
            Map<String, String> txtFile = new HashMap<>();
            txtFile.put("name", doc.getName().replaceAll("\\.(txt|md)$", "") + ".txt");
            txtFile.put("url", "/api/exports/" + result.get("exportId") + "/" + txtFile.get("name"));
            exportedFiles.add(txtFile);

            if (annotations.size() > 0) {
                Map<String, String> csvFile = new HashMap<>();
                csvFile.put("name", doc.getName().replaceAll("\\.(txt|md)$", "") + "+实体标注.csv");
                csvFile.put("url", "/api/exports/" + result.get("exportId") + "/" + csvFile.get("name"));
                exportedFiles.add(csvFile);
            }
        }

        result.put("exportedFiles", exportedFiles);

        return result;
    }
}
