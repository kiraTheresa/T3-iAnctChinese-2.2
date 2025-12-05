package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Project;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.ProjectRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ProjectCreateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ProjectUpdateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 项目服务实现类
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * 根据用户ID获取项目列表
     * @param userId 用户ID
     * @return 项目列表
     */
    @Override
    public List<Project> getProjectsByUserId(Integer userId) {
        return projectRepository.findByUserId(userId);
    }

    /**
     * 创建项目
     * @param createRequest 项目创建请求
     * @return 新创建的项目
     */
    @Override
    @Transactional
    public Project createProject(ProjectCreateRequest createRequest) {
        // 验证必填字段
        if (createRequest.getUserId() == null || createRequest.getName() == null || createRequest.getName().isEmpty()) {
            throw new RuntimeException("缺少必要参数");
        }

        // 生成项目ID
        String projectId = System.currentTimeMillis() + "" + UUID.randomUUID().toString().replace("-", "").substring(0, 9);

        // 创建项目
        Project project = new Project();
        project.setId(projectId);
        project.setUserId(createRequest.getUserId());
        project.setName(createRequest.getName());
        project.setDescription(createRequest.getDescription() != null ? createRequest.getDescription() : "");

        return projectRepository.save(project);
    }

    /**
     * 更新项目
     * @param projectId 项目ID
     * @param updateRequest 项目更新请求
     * @return 更新后的项目
     */
    @Override
    @Transactional
    public Project updateProject(String projectId, ProjectUpdateRequest updateRequest) {
        // 验证项目是否存在
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            throw new RuntimeException("项目不存在");
        }

        Project project = optionalProject.get();

        // 更新项目信息
        if (updateRequest.getName() != null) {
            project.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            project.setDescription(updateRequest.getDescription());
        }

        return projectRepository.save(project);
    }

    /**
     * 删除项目
     * @param projectId 项目ID
     */
    @Override
    @Transactional
    public void deleteProject(String projectId) {
        // 验证项目是否存在
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            throw new RuntimeException("项目不存在");
        }

        projectRepository.deleteById(projectId);
    }

    /**
     * 根据ID获取项目
     * @param projectId 项目ID
     * @return 项目信息
     */
    @Override
    public Optional<Project> getProjectById(String projectId) {
        return projectRepository.findById(projectId);
    }
}
