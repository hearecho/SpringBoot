package com.echo.springboot206security.repository;

import com.echo.springboot206security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    UserEntity findByUsername(String username);
}
