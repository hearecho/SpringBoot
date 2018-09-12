package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity,Long> {
}
