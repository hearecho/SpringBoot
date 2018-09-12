package com.echo.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author echo
 */
@Controller
public class MainController {
    @GetMapping("/biliinfo")
    public String getInfo() {
        return "datashow/bilibili";
    }

    @GetMapping("/sql")
    public String sql() {
        return "datashow/sql";
    }

    @GetMapping("lifegame")
    public String Test() {
        return "datashow/lifegame";
    }

}
