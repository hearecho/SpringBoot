package com.echo.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/biliinfo")
    public String getInfo() {
        return "datashow/bilibili";
    }

    @GetMapping("lifegame")
    public String Test() {
        return "lib/lifegame";
    }

}
