package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Project;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ProjectCreateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ProjectUpdateRequest;

import java.util.List;
import java.util.Optional;

/**
 * 项目服务接口
 */
public interface ProjectService {

    /**
     * 根据用户ID获取项目列表
     * @param userId 用户ID
     * @return 项目列表
     */
    List<Project> getProjectsByUserId(Integer userId);

    /**
     * 创建项目
     * @param createRequest 项目创建请求
     * @return 新创建的项目
     */
    Project createProject(ProjectCreateRequest createRequest);

    /**
     * 更新项目
     * @param projectId 项目ID
     * @param updateRequest 项目更新请求
     * @return 更新后的项目
     */
    Project updateProject(String projectId, ProjectUpdateRequest updateRequest);

    /**
     * 删除项目
     * @param projectId 项目ID
     */
    void deleteProject(String projectId);

    /**
     * 根据ID获取项目
     * @param projectId 项目ID
     * @return 项目信息
     */
    Optional<Project> getProjectById(String projectId);
}
