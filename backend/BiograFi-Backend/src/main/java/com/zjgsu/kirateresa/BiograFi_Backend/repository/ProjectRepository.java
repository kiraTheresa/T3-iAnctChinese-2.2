package com.zjgsu.kirateresa.BiograFi_Backend.repository;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目数据访问接口
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    /**
     * 根据用户ID查找项目列表
     * @param userId 用户ID
     * @return 项目列表
     */
    List<Project> findByUserId(Integer userId);

}
