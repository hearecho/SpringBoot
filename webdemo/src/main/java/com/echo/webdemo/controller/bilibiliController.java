package com.echo.webdemo.controller;

import com.alibaba.fastjson.JSON;
import com.echo.webdemo.service.DataService;
import com.echo.webdemo.service.bilibiliSpider;
import com.echo.webdemo.service.impl.DataServiceImpl;
import com.echo.webdemo.service.impl.bilibiliSpiderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
/**
 * 管理bilibili爬虫测试的请求
 */
public class bilibiliController {

    bilibiliSpider spider = new bilibiliSpiderImpl();

    @PostMapping("/bili/getfollow")
    @ResponseBody
    public String getfollow(@RequestParam("mid")int mid) throws IOException {
//        StringBuffer buffer = new StringBuffer();
//        buffer.append(spider.getfollow(mid));
//        buffer.append(spider.getUserInfo(mid));

        Map<String,String> follow ;
        Map<String,String> info;
        follow = spider.getfollow(mid);
        info = spider.getUserInfo(mid);
        info.putAll(follow);
        String data = JSON.toJSONString(info);
        System.out.println(data);
        return data;
    }



}
