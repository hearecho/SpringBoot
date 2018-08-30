package com.echo.webdemo.repository;

import com.echo.webdemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    /**
     * 通过username 查找
     * @param username
     * @return
     */
    public UserEntity findByUsername(String username);

}
