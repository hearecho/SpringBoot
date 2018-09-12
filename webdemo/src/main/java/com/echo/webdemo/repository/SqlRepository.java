package com.echo.webdemo.repository;

import com.echo.webdemo.entity.SqlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 必须为接口
 */
public interface SqlRepository extends JpaRepository<SqlEntity, Integer> {

}
