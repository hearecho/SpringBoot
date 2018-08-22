package com.echo.springboot204config.controller;

import com.echo.springboot204config.config.Person;
import com.echo.springboot204config.config.PersonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    Person person;

    @RequestMapping("/person")
    public String getall() {
        return person.toString();
    }

}
