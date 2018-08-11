package com.echo.springboot202mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.echo.springboot202mybatis.mapper")
public class Springboot202MybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot202MybatisApplication.class, args);
	}
}
