package com.zjgsu.kirateresa.BiograFi_Backend.service.impl;

import com.zjgsu.kirateresa.BiograFi_Backend.model.User;
import com.zjgsu.kirateresa.BiograFi_Backend.repository.UserRepository;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserLoginRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserRegisterRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.dto.UserUpdateRequest;
import com.zjgsu.kirateresa.BiograFi_Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 用户信息
     */
    @Override
    public User login(UserLoginRequest loginRequest) {
        // 根据用户名查找用户
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("用户名不存在");
        }
        
        User user = optionalUser.get();
        
        // 验证密码
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        // 验证用户是否激活
        if (!user.getIsActive()) {
            throw new RuntimeException("账号已被禁用");
        }
        
        // 更新最后登录时间
        updateLastLogin(user.getId());
        
        return user;
    }

    /**
     * 用户注册
     * @param registerRequest 注册请求
     * @return 新创建的用户
     */
    @Override
    @Transactional
    public User register(UserRegisterRequest registerRequest) {
        // 验证用户名长度
        if (registerRequest.getUsername().length() < 3 || registerRequest.getUsername().length() > 20) {
            throw new RuntimeException("用户名长度应在3-20个字符之间");
        }
        
        // 验证邮箱格式
        if (!registerRequest.getEmail().contains("@")) {
            throw new RuntimeException("请提供有效的邮箱地址");
        }
        
        // 验证密码长度
        if (registerRequest.getPassword().length() < 6) {
            throw new RuntimeException("密码至少需要6个字符");
        }
        
        // 检查用户名是否已存在
        Optional<User> existingUser = userRepository.findByUsername(registerRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("用户名已被注册");
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setIsActive(true);
        
        return userRepository.save(user);
    }

    /**
     * 更新用户信息
     * @param userId 用户ID
     * @param updateRequest 更新请求
     * @return 更新后的用户
     */
    @Override
    @Transactional
    public User updateUser(Integer userId, UserUpdateRequest updateRequest) {
        // 根据ID查找用户
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("用户不存在");
        }
        
        User user = optionalUser.get();
        
        // 检查邮箱是否被其他用户使用
        if (updateRequest.getEmail() != null) {
            if (userRepository.existsByEmailAndIdNot(updateRequest.getEmail(), userId)) {
                throw new RuntimeException("邮箱已被其他用户使用");
            }
            user.setEmail(updateRequest.getEmail());
        }
        
        // 更新密码
        if (updateRequest.getPassword() != null) {
            if (updateRequest.getPassword().length() < 6) {
                throw new RuntimeException("密码至少需要6个字符");
            }
            user.setPassword(updateRequest.getPassword());
        }
        
        return userRepository.save(user);
    }

    /**
     * 获取所有用户
     * @return 用户列表
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 根据ID查找用户
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public Optional<User> findUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 更新用户最后登录时间
     * @param userId 用户ID
     */
    @Override
    @Transactional
    public void updateLastLogin(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
        }
    }
}
