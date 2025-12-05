package com.zjgsu.kirateresa.BiograFi_Backend.repository;

import com.zjgsu.kirateresa.BiograFi_Backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户数据访问接口
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱查找用户
     * @param email 邮箱
     * @return 用户信息
     */
    Optional<User> findByEmail(String email);

    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 检查邮箱是否已存在（排除指定用户）
     * @param email 邮箱
     * @param id 用户ID
     * @return 是否存在
     */
    boolean existsByEmailAndIdNot(String email, Integer id);

}
