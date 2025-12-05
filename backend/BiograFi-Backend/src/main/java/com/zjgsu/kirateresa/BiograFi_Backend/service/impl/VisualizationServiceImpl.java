package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.VisualizationOverview;
import com.zjgsu.kirateresa.BiograFi_Backend.model.Document;
import com.zjgsu.kirateresa.BiograFi_Backend.model.EntityAnnotation;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.DocumentRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.EntityAnnotationRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.service.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 可视化分析服务实现类
 */
@Service
public class VisualizationServiceImpl implements VisualizationService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityAnnotationRepository annotationRepository;

    /**
     * 获取文档可视化总览统计
     * @param documentId 文档ID
     * @return 可视化总览统计
     */
    @Override
    public VisualizationOverview getVisualizationOverview(String documentId) {
        // 获取文档内容
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (!optionalDocument.isPresent()) {
            throw new RuntimeException("文档不存在");
        }

        Document document = optionalDocument.get();
        // 计算总字符数
        Integer totalChars = document.getContent() != null ? document.getContent().length() : 0;

        // 获取标签统计
        List<Map<String, Object>> countResults = annotationRepository.countByLabel(documentId);
        Map<String, Long> labelCounts = countResults.stream()
                .collect(Collectors.toMap(
                        result -> (String) result.get("label"),
                        result -> ((Number) result.get("count")).longValue()
                ));

        return new VisualizationOverview(totalChars, labelCounts);
    }

    /**
     * 获取地点可视化数据
     * @param documentId 文档ID
     * @return 地点可视化数据
     */
    @Override
    public Map<String, Object> getLocationsVisualization(String documentId) {
        // 获取所有地名实体
        List<EntityAnnotation> locationAnnotations = annotationRepository.findByDocumentIdAndLabel(documentId, "地名");

        // 聚合地名实体
        Map<String, Long> locationCounts = locationAnnotations.stream()
                .collect(Collectors.groupingBy(
                        ann -> ann.getTextContent() != null ? ann.getTextContent() : (documentRepository.findById(documentId).get().getContent().substring(ann.getStartIndex(), ann.getEndIndex())),
                        Collectors.counting()
                ));

        // 构建地点数据列表
        List<Map<String, Object>> locations = new ArrayList<>();
        for (Map.Entry<String, Long> entry : locationCounts.entrySet()) {
            Map<String, Object> locationData = new HashMap<>();
            locationData.put("name", entry.getKey());
            locationData.put("count", entry.getValue());
            // 这里可以添加真实的地理坐标，目前使用示例数据
            locationData.put("coordinates", Arrays.asList(116.4074, 39.9042)); // 示例坐标（北京）
            locationData.put("matchInfo", Map.of(
                    "original", entry.getKey(),
                    "matched", entry.getKey(),
                    "confidence", "high",
                    "type", "historical"
            ));
            locations.add(locationData);
        }

        return Map.of("success", true, "locations", locations);
    }

    /**
     * 获取人物关系图数据
     * @param documentId 文档ID
     * @return 人物关系图数据
     */
    @Override
    public Map<String, Object> getRelationshipsVisualization(String documentId) {
        // 获取所有人物实体
        List<EntityAnnotation> personAnnotations = annotationRepository.findByDocumentIdAndLabel(documentId, "人物");

        // 统计人物出现频率
        Map<String, Long> personFrequency = personAnnotations.stream()
                .collect(Collectors.groupingBy(
                        ann -> ann.getTextContent() != null ? ann.getTextContent() : (documentRepository.findById(documentId).get().getContent().substring(ann.getStartIndex(), ann.getEndIndex())),
                        Collectors.counting()
                ));

        // 确定中心人物（出现频率最高的人物）
        String centerPerson = personFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        // 构建节点列表
        List<Map<String, Object>> nodes = new ArrayList<>();
        for (Map.Entry<String, Long> entry : personFrequency.entrySet()) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", entry.getKey());
            node.put("name", entry.getKey());
            node.put("isCenter", entry.getKey().equals(centerPerson));
            node.put("frequency", entry.getValue());
            nodes.add(node);
        }

        // 构建连接关系（这里使用简化的逻辑，实际应该基于文本上下文计算关系强度）
        List<Map<String, Object>> links = new ArrayList<>();
        if (nodes.size() > 1) {
            // 示例：为中心人物与其他人物创建连接
            for (Map.Entry<String, Long> entry : personFrequency.entrySet()) {
                if (!entry.getKey().equals(centerPerson)) {
                    Map<String, Object> link = new HashMap<>();
                    link.put("source", centerPerson);
                    link.put("target", entry.getKey());
                    link.put("strength", Math.min(entry.getValue(), 5)); // 简化的关系强度计算
                    link.put("label", "熟悉");
                    links.add(link);
                }
            }
        }

        return Map.of(
                "success", true,
                "centerPerson", centerPerson,
                "nodes", nodes,
                "links", links
        );
    }

    /**
     * 获取时间轴数据
     * @param documentId 文档ID
     * @return 时间轴数据
     */
    @Override
    public Map<String, Object> getTimelineVisualization(String documentId) {
        // 获取所有时间实体
        List<EntityAnnotation> timeAnnotations = annotationRepository.findByDocumentIdAndLabelOrderByStartIndexAsc(documentId, "时间");

        // 构建事件列表
        List<Map<String, Object>> events = new ArrayList<>();
        Optional<Document> optionalDocument = documentRepository.findById(documentId);
        if (optionalDocument.isPresent()) {
            Document document = optionalDocument.get();
            String content = document.getContent() != null ? document.getContent() : "";

            for (int i = 0; i < timeAnnotations.size(); i++) {
                EntityAnnotation annotation = timeAnnotations.get(i);
                Map<String, Object> event = new HashMap<>();
                event.put("index", i);
                event.put("start", annotation.getStartIndex());
                event.put("end", annotation.getEndIndex());
                event.put("text", annotation.getTextContent() != null ? annotation.getTextContent() : content.substring(annotation.getStartIndex(), annotation.getEndIndex()));
                event.put("normalizedTime", ""); // 简化处理，实际应该进行时间标准化
                // 获取上下文（前后各50个字符）
                int contextStart = Math.max(0, annotation.getStartIndex() - 50);
                int contextEnd = Math.min(content.length(), annotation.getEndIndex() + 50);
                String context = content.substring(contextStart, contextEnd);
                event.put("context", "..." + context + "...");
                events.add(event);
            }
        }

        return Map.of("success", true, "events", events);
    }
}
