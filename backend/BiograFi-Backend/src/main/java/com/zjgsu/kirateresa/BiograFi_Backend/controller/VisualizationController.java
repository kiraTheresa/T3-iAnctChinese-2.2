package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.VisualizationOverview;
import com.zjgsu.kirateresa.BiograFi_Backend.service.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 可视化分析Controller
 */
@RestController
@RequestMapping("/api")
public class VisualizationController {

    @Autowired
    private VisualizationService visualizationService;

    /**
     * 获取可视化总览统计
     * @param documentId 文档ID
     * @return 可视化总览统计
     */
    @GetMapping("/visualization/overview")
    public ResponseEntity<ApiResponse<VisualizationOverview>> getOverview(
            @RequestParam("documentId") String documentId) {
        try {
            VisualizationOverview overview = visualizationService.getVisualizationOverview(documentId);
            return ResponseEntity.ok(ApiResponse.success(overview));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 获取地点可视化数据
     * @param documentId 文档ID
     * @return 地点可视化数据
     */
    @GetMapping("/visualization/locations")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getLocations(
            @RequestParam("documentId") String documentId) {
        try {
            Map<String, Object> locationsData = visualizationService.getLocationsVisualization(documentId);
            return ResponseEntity.ok(ApiResponse.success(locationsData));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 获取人物关系图数据
     * @param documentId 文档ID
     * @return 人物关系图数据
     */
    @GetMapping("/visualization/relationships")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRelationships(
            @RequestParam("documentId") String documentId) {
        try {
            Map<String, Object> relationshipsData = visualizationService.getRelationshipsVisualization(documentId);
            return ResponseEntity.ok(ApiResponse.success(relationshipsData));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 获取时间轴数据
     * @param documentId 文档ID
     * @return 时间轴数据
     */
    @GetMapping("/visualization/timeline")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTimeline(
            @RequestParam("documentId") String documentId) {
        try {
            Map<String, Object> timelineData = visualizationService.getTimelineVisualization(documentId);
            return ResponseEntity.ok(ApiResponse.success(timelineData));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }
}
