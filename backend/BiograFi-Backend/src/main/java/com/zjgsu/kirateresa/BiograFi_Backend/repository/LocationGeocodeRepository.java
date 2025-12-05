package com.zjgsu.kirateresa.BiograFi_Backend.repository;

import com.zjgsu.kirateresa.BiograFi_Backend.model.LocationGeocode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 地名坐标缓存数据访问接口
 */
@Repository
public interface LocationGeocodeRepository extends JpaRepository<LocationGeocode, Integer> {

    /**
     * 根据名称查找地名坐标
     * @param name 地名
     * @return 地名坐标信息
     */
    Optional<LocationGeocode> findByName(String name);

    /**
     * 检查地名是否已存在
     * @param name 地名
     * @return 是否存在
     */
    boolean existsByName(String name);

}
