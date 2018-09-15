package com.echo.springboot206security.controller;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView login(@RequestParam(name = "error",required = false) String error,
                        ModelAndView model) {
       model.setViewName("login");
       if (error != null) {
           model.addObject("error","登陆错误");
       }
       return model;
    }

}
