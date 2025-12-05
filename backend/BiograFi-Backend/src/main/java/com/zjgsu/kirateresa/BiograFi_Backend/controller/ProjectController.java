package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.model.Project;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ProjectCreateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ProjectUpdateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目Controller
 */
@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取用户项目列表
     * @param userId 用户ID
     * @return 项目列表
     */
    @GetMapping("/projects")
    public ResponseEntity<ApiResponse<List<Project>>> getProjects(@RequestParam("userId") Integer userId) {
        try {
            List<Project> projects = projectService.getProjectsByUserId(userId);
            return ResponseEntity.ok(ApiResponse.success(projects));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }

    /**
     * 创建项目
     * @param createRequest 项目创建请求
     * @return 新创建的项目
     */
    @PostMapping("/projects")
    public ResponseEntity<ApiResponse<Project>> createProject(@RequestBody ProjectCreateRequest createRequest) {
        try {
            Project project = projectService.createProject(createRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(project));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 更新项目
     * @param projectId 项目ID
     * @param updateRequest 项目更新请求
     * @return 更新后的项目
     */
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<Project>> updateProject(@PathVariable String projectId,
                                                             @RequestBody ProjectUpdateRequest updateRequest) {
        try {
            Project project = projectService.updateProject(projectId, updateRequest);
            return ResponseEntity.ok(ApiResponse.success(project));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 删除项目
     * @param projectId 项目ID
     * @return 删除结果
     */
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable String projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取项目详情
     * @param projectId 项目ID
     * @return 项目详情
     */
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<ApiResponse<Project>> getProjectById(@PathVariable String projectId) {
        try {
            return projectService.getProjectById(projectId)
                    .map(project -> ResponseEntity.ok(ApiResponse.success(project)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ApiResponse.error("项目不存在")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("服务器错误"));
        }
    }
}
