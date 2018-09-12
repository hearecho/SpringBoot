package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
}
