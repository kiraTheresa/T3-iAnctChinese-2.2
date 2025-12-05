package com.zjgsu.kirateresa.BiograFi_Backend.dto;

import lombok.Data;

/**
 * 用户更新请求DTO
 */
@Data
public class UserUpdateRequest {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;
}
