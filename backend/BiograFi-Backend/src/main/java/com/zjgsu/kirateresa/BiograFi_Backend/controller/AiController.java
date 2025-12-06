package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.*;
import com.zjgsu.kirateresa.BiograFi_Backend.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AI 控制器，处理 AI 相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*") // 允许跨域请求
public class AiController {

    @Autowired
    private AiService aiService;

    /**
     * 文本分析接口
     * @param request AI 分析请求
     * @return 分析结果
     */
    @PostMapping("/analyze")
    public ResponseEntity<ApiResponse<AiResponse>> analyzeText(@RequestBody AiAnalyzeRequest request) {
        try {
            // 参数验证
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("请提供要分析的文本"));
            }

            // 调用服务层
            String result = aiService.analyzeText(request.getText(), request.getModel());

            // 构建响应
            AiResponse response = new AiResponse();
            response.setResult(result);

            return ResponseEntity.ok(ApiResponse.success("分析成功", response));
        } catch (IllegalArgumentException e) {
            // 参数错误
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            // 其他异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("分析失败: " + e.getMessage()));
        }
    }

    /**
     * 问答系统接口
     * @param request AI 问答请求
     * @return 回答结果
     */
    @PostMapping("/qa")
    public ResponseEntity<ApiResponse<AiResponse>> qaText(@RequestBody AiQaRequest request) {
        try {
            // 参数验证
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("请提供原文"));
            }
            if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("请提供问题"));
            }

            // 调用服务层
            String result = aiService.qaText(request.getText(), request.getQuestion(), request.getModel());

            // 构建响应
            AiResponse response = new AiResponse();
            response.setResult(result);

            return ResponseEntity.ok(ApiResponse.success("问答成功", response));
        } catch (IllegalArgumentException e) {
            // 参数错误
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            // 其他异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("问答失败: " + e.getMessage()));
        }
    }

    /**
     * 自动标注接口
     * @param request AI 自动标注请求
     * @return 标注结果
     */
    @PostMapping("/auto-annotate")
    public ResponseEntity<ApiResponse<AutoAnnotationResponse>> autoAnnotate(@RequestBody AiAutoAnnotateRequest request) {
        try {
            // 参数验证
            if (request.getText() == null || request.getText().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("请提供要标注的文本"));
            }

            // 调用服务层
            var annotations = aiService.autoAnnotate(request.getText());

            // 构建响应
            AutoAnnotationResponse response = new AutoAnnotationResponse();
            response.setAnnotations(annotations);

            return ResponseEntity.ok(ApiResponse.success("自动标注成功", response));
        } catch (IllegalArgumentException e) {
            // 参数错误
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            // 其他异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("自动标注失败: " + e.getMessage()));
        }
    }

    /**
     * 健康检查接口
     * @return 服务状态
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        try {
            String status = aiService.healthCheck();
            return ResponseEntity.ok(ApiResponse.success("AI服务运行正常: " + status, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ApiResponse.error("AI服务异常: " + e.getMessage()));
        }
    }
}