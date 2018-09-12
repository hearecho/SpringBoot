package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.PeopleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository<PeopleEntity,Long> {

}
