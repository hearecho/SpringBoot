package com.echo.springboot202mybatis.controller;

import com.echo.springboot202mybatis.domain.User;
import com.echo.springboot202mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;

    /***
     * http://localhost:8080/user/getall
     * @return
     */
    @GetMapping("/getall")
    public List<User> getall() {
        return userMapper.getall();
    }

    /**
     * http://localhost:8080/user/getone?username=echo
     * @param username
     * @return
     */
    @GetMapping("/getone")
    public User getByUsername(@RequestParam(name = "username",required = true) String username) {
        return userMapper.getByUsername(username);
    }
}
