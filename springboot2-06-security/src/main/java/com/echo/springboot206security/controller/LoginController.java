package com.echo.springboot206security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author echo
 */
@Controller
public class LoginController {
   @GetMapping(value = {"/","/home"})
    public String home() {
       return "home";
   }

   @GetMapping("/hello")
    public String hello() {
       return "hello";
   }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
