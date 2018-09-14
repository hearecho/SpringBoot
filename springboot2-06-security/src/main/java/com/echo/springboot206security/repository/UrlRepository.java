package com.echo.springboot206security.repository;

import com.echo.springboot206security.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity,Integer> {
}
