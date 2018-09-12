package com.echo.webdemo.controller;

import com.echo.webdemo.entity.SqlEntity;
import com.echo.webdemo.repository.SqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于查看sql操作
 */
@RestController
public class SqlOperateController {
    @Autowired
    SqlRepository sqlRepository;

    @GetMapping("/sql/insert")
    public String insert(@RequestParam(value = "title") String title,
                         @RequestParam(value = "datetime") long datetime,
                         @RequestParam(value = "type") String type) {
        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setTime(datetime);
        sqlEntity.setTitle(title);
        sqlEntity.setType(type);
        sqlRepository.save(sqlEntity);
        return "";
    }

    @GetMapping("/sql/query")
    public String query(@RequestParam(value = "title") String title,
                        @RequestParam(value = "datetime") long datetime,
                        @RequestParam(value = "type") String type) {


        return "";
    }
}
