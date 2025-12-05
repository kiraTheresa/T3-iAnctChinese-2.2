package com.zjgsu.kirateresa.BiograFi_Backend.service;

import com.zjgsu.kirateresa.BiograFi_Backend.model.User;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserLoginRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserRegisterRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 用户信息
     */
    User login(UserLoginRequest loginRequest);

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 新创建的用户
     */
    User register(UserRegisterRequest registerRequest);

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param updateRequest 更新请求
     * @return 更新后的用户
     */
    User updateUser(Integer userId, UserUpdateRequest updateRequest);

    /**
     * 获取所有用户
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 根据ID查找用户
     * @param userId 用户ID
     * @return 用户信息
     */
    Optional<User> findUserById(Integer userId);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    Optional<User> findUserByUsername(String username);

    /**
     * 更新用户最后登录时间
     * @param userId 用户ID
     */
    void updateLastLogin(Integer userId);
}
