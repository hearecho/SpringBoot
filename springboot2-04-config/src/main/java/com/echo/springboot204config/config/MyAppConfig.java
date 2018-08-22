package com.echo.springboot204config.config;

import com.echo.springboot204config.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAppConfig {
    @Bean
    public HelloService helloService() {
        System.out.println("配置类给容器添加了HelloService组件");
        return new HelloService();
    }
}
