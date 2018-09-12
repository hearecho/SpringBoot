package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.ArticleEntity;
import com.echo.springboot201jpa.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity,Long> {
}
