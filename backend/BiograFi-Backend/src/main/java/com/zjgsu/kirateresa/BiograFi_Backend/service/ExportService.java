package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.ExportRequest;

import java.util.Map;

/**
 * 导出服务接口
 */
public interface ExportService {

    /**
     * 导出文档及标注
     * @param exportRequest 导出请求
     * @return 导出结果
     */
    Map<String, Object> exportDocuments(ExportRequest exportRequest);
}
