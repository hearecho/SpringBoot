package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity,Integer> {
}
