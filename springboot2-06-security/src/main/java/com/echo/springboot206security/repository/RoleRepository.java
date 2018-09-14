package com.echo.springboot206security.repository;

import com.echo.springboot206security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
}
