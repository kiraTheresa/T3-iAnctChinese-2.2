package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.SegmentRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.SegmentResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分词控制器，处理文本分词相关的 HTTP 请求
 */
@RestController
@RequestMapping("/api")
public class SegmentController {

    @Autowired
    private SegmentService segmentService;

    /**
     * 文本分词接口
     * @param request 分词请求
     * @return 分词结果
     */
    @PostMapping("/segment")
    public ResponseEntity<ApiResponse<SegmentResponse>> segmentText(@RequestBody SegmentRequest request) {
        try {
            if (request.getText() == null || request.getText().isEmpty()) {
                return ResponseEntity.badRequest().body(ApiResponse.error("请提供要分词的文本"));
            }

            var tokens = segmentService.segmentText(request.getText());
            SegmentResponse response = new SegmentResponse();
            response.setTokens(tokens);
            
            return ResponseEntity.ok(ApiResponse.success("分词成功", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("分词失败: " + e.getMessage()));
        }
    }
}
