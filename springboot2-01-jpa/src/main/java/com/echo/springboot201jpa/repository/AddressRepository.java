package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
}
