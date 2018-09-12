package com.echo.webdemo.controller;

import com.echo.webdemo.entity.UserEntity;
import com.echo.webdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;
    @GetMapping(value = "/")
    public String index() {
        return "login/login";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping(value ="/user/login")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        Map<String,Object> map,
                        HttpSession session) {

        String pw = userRepository.findByUsername(username).getPassword();
        if(!StringUtils.isEmpty(username) && pw.equals(password)){
            //登录成功,防止重复提交
            session.setAttribute("loginUser", username);
            return "main/home";
        }else{
            map.put("msg", "用户名密码错误");
            return "login/login";
        }

    }
}
