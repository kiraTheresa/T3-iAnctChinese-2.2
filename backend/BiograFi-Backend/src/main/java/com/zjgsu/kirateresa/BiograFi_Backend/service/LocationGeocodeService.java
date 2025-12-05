package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.model.LocationGeocode;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.LocationCacheRequest;

import java.util.Optional;

/**
 * 地名坐标缓存服务接口
 */
public interface LocationGeocodeService {

    /**
     * 根据名称获取地名坐标
     * @param name 地名
     * @return 地名坐标信息
     */
    Optional<LocationGeocode> getLocationGeocode(String name);

    /**
     * 更新或创建地名坐标缓存
     * @param locationCacheRequest 地名坐标缓存请求
     * @return 更新后的地名坐标信息
     */
    LocationGeocode saveLocationGeocode(LocationCacheRequest locationCacheRequest);
}
