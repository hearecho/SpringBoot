package com.echo.springboot206security.controller;
import com.echo.springboot206security.bean.UserRoleBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author echo
 */
@Controller
public class LoginController {

   @RequestMapping(value = {"/","/home"},method = {RequestMethod.POST,RequestMethod.GET})
    public String home() {
       SecurityContext securityContext = SecurityContextHolder.getContext();
       Authentication authentication = securityContext.getAuthentication();
//       UserRoleBean user = (UserRoleBean) authentication.getPrincipal();
//       System.out.println(user.getUsername());
//       System.out.println(user.getPassword());
//       System.out.println(user.getAuthorities());
       return "home";
   }

   @RequestMapping(value = "/hello",method = {RequestMethod.POST,RequestMethod.GET})
    public String hello() {
       return "hello";
   }


    @RequestMapping(value = "/admin",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public String admin() {
       return "test";
    }
}
