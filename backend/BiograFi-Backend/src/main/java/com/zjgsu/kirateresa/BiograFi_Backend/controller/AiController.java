package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.*;
import com.zjgsu.kirateresa.BiograFi_Backend.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI 控制器，处理 AI 相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api/ai")
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
            if (request.getText() == null || request.getText().isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "请提供要分析的文本", null));
            }

            String result = aiService.analyzeText(request.getText(), request.getModel());
            AiResponse response = new AiResponse();
            response.setResult(result);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "分析成功", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "分析失败: " + e.getMessage(), null));
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
            if (request.getText() == null || request.getText().isEmpty() || 
                request.getQuestion() == null || request.getQuestion().isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "请提供原文和问题", null));
            }

            String result = aiService.qaText(request.getText(), request.getQuestion(), request.getModel());
            AiResponse response = new AiResponse();
            response.setResult(result);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "问答成功", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "问答失败: " + e.getMessage(), null));
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
            if (request.getText() == null || request.getText().isEmpty()) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "请提供要标注的文本", null));
            }

            var annotations = aiService.autoAnnotate(request.getText());
            AutoAnnotationResponse response = new AutoAnnotationResponse();
            response.setAnnotations(annotations);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "自动标注成功", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "自动标注失败: " + e.getMessage(), null));
        }
    }
}
