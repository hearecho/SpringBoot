package com.echo.springboot204config.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PersonValue {

    @Value("${person.last-name}")
    private String lastName;

    @Value("#{11*2}")
    private Integer age;
    @Value("${person.boss}")
    private Boolean boss;
//    不支持复杂类型,类 map等
//    @Value("${person.maps}")
    private Map<String,Object> maps;
    @Value("${person.lists}")
    private List<Object> lists;

    @Override
    public String toString() {
        return lastName+"\t"+age+"\t"+boss+"\t"+maps+"\t"+lists;
    }
}
