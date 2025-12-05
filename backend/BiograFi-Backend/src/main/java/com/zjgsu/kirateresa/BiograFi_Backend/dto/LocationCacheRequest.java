package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 地名坐标缓存请求DTO
 */
@Data
public class LocationCacheRequest {
    /**
     * 地名
     */
    private String name;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 匹配的地名
     */
    private String matchedName;

    /**
     * 置信度，可选值：high, medium, low
     */
    private String confidence;
}
