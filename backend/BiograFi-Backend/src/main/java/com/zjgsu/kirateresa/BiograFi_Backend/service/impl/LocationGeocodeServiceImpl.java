package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.model.LocationGeocode;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.LocationGeocodeRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.LocationCacheRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.LocationGeocodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 地名坐标缓存服务实现类
 */
@Service
public class LocationGeocodeServiceImpl implements LocationGeocodeService {

    @Autowired
    private LocationGeocodeRepository locationGeocodeRepository;

    /**
     * 根据名称获取地名坐标
     * @param name 地名
     * @return 地名坐标信息
     */
    @Override
    public Optional<LocationGeocode> getLocationGeocode(String name) {
        return locationGeocodeRepository.findByName(name);
    }

    /**
     * 更新或创建地名坐标缓存
     * @param locationCacheRequest 地名坐标缓存请求
     * @return 更新后的地名坐标信息
     */
    @Override
    @Transactional
    public LocationGeocode saveLocationGeocode(LocationCacheRequest locationCacheRequest) {
        // 验证必填字段
        if (locationCacheRequest.getName() == null || locationCacheRequest.getName().isEmpty()) {
            throw new RuntimeException("地名不能为空");
        }

        // 查找现有记录
        Optional<LocationGeocode> optionalGeocode = locationGeocodeRepository.findByName(locationCacheRequest.getName());
        LocationGeocode geocode;

        if (optionalGeocode.isPresent()) {
            // 更新现有记录
            geocode = optionalGeocode.get();
        } else {
            // 创建新记录
            geocode = new LocationGeocode();
            geocode.setName(locationCacheRequest.getName());
        }

        // 更新坐标信息
        if (locationCacheRequest.getLng() != null) {
            geocode.setLng(BigDecimal.valueOf(locationCacheRequest.getLng()));
        }
        if (locationCacheRequest.getLat() != null) {
            geocode.setLat(BigDecimal.valueOf(locationCacheRequest.getLat()));
        }
        if (locationCacheRequest.getMatchedName() != null) {
            geocode.setMatchedName(locationCacheRequest.getMatchedName());
        }
        if (locationCacheRequest.getConfidence() != null) {
            try {
                geocode.setConfidence(LocationGeocode.ConfidenceLevel.valueOf(locationCacheRequest.getConfidence()));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("置信度值无效，可选值：high, medium, low");
            }
        }

        return locationGeocodeRepository.save(geocode);
    }
}
