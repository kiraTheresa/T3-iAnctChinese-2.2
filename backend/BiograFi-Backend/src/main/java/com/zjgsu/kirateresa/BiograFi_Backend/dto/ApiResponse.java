package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用API响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    /**
     * 成功状态
     */
    private boolean success;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 成功响应
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    /**
     * 成功响应（带消息）
     * @param message 成功消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        // 注意：这里message参数暂时未使用，保留用于兼容现有代码
        return new ApiResponse<>(true, data, null);
    }

    /**
     * 错误响应
     * @param error 错误信息
     * @param <T> 数据类型
     * @return 错误响应
     */
    public static <T> ApiResponse<T> error(String error) {
        return new ApiResponse<>(false, null, error);
    }
}
