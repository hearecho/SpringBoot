package com.echo.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class TestController {

    @GetMapping("/test/{homeId}")
    public String testHome(@PathVariable("homeId") int homeId) {
//        System.out.println("访问的房间号位:"+homeId);
        return "test";
    }
}
