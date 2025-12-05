package com.zjgsu.kirateresa.BiograFi_Backend.controller;

import com.zjgsu.kirateresa.BiograFi_Backend.model.User;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.ApiResponse;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserLoginRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserRegisterRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserUpdateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody UserLoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest);
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserRegisterRequest registerRequest) {
        try {
            User user = userService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param updateRequest 更新请求
     * @return 更新结果
     */
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Integer userId, 
                                                       @RequestBody UserUpdateRequest updateRequest) {
        try {
            User user = userService.updateUser(userId, updateRequest);
            return ResponseEntity.ok(ApiResponse.success(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * 获取用户详情
     * @param userId 用户ID
     * @return 用户详情
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer userId) {
        Optional<User> optionalUser = userService.findUserById(userId);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success(optionalUser.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("用户不存在"));
        }
    }
}
