package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.model.LocationGeocode;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ExportRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.LocationCacheRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.ExportService;
import com.zjgsu.kirateresa.BiograFi_Backend.service.LocationGeocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * 导出与缓存Controller
 */
@RestController
@RequestMapping("/api")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @Autowired
    private LocationGeocodeService locationGeocodeService;

    /**
     * 导出文档及标注
     * @param exportRequest 导出请求
     * @return 导出结果
     */
    @PostMapping("/export-documents")
    public ResponseEntity<ApiResponse<Map<String, Object>>> exportDocuments(@RequestBody ExportRequest exportRequest) {
        try {
            Map<String, Object> result = exportService.exportDocuments(exportRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(result));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 下载导出文件
     * @param exportId 导出ID
     * @param fileName 文件名
     * @return 文件下载响应
     */
    @GetMapping("/exports/{exportId}/{fileName}")
    public ResponseEntity<ApiResponse<String>> downloadExportFile(
            @PathVariable String exportId,
            @PathVariable String fileName) {
        try {
            // 简化实现，实际应该返回真实文件
            String fileUrl = "/api/exports/" + exportId + "/" + fileName;
            return ResponseEntity.ok(ApiResponse.success("文件下载功能开发中，当前文件路径: " + fileUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 查询地名坐标缓存
     * @param name 地名
     * @return 地名坐标信息
     */
    @GetMapping("/visualization/locations/cache")
    public ResponseEntity<ApiResponse<LocationGeocode>> getLocationCache(@RequestParam("name") String name) {
        try {
            Optional<LocationGeocode> optionalGeocode = locationGeocodeService.getLocationGeocode(name);
            if (optionalGeocode.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(optionalGeocode.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("未找到该地名的坐标缓存"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 更新地名坐标缓存
     * @param locationCacheRequest 地名坐标缓存请求
     * @return 更新后的地名坐标信息
     */
    @PostMapping("/visualization/locations/cache")
    public ResponseEntity<ApiResponse<LocationGeocode>> updateLocationCache(@RequestBody LocationCacheRequest locationCacheRequest) {
        try {
            LocationGeocode geocode = locationGeocodeService.saveLocationGeocode(locationCacheRequest);
            return ResponseEntity.ok(ApiResponse.success(geocode));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }
}
