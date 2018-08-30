package com.echo.webdemo.service;

import com.echo.webdemo.service.impl.DataServiceImpl;

import java.io.IOException;
import java.util.Map;

public interface bilibiliSpider {

//    String getfollow(int mid) throws IOException;
    Map<String,String> getUserInfo(int mid) throws IOException;

    Map<String,String> getfollow(int mid) throws IOException;
}
