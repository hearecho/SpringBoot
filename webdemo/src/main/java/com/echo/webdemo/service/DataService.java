package com.echo.webdemo.service;


import java.io.IOException;
import java.util.Map;

public interface DataService {
    /**
     * 模拟Post请求,可以获取简单的post请求数据
     *
     * @param form
     * @param headers
     * @param url
     * @return
     * @throws IOException
     */
    String PostReq(Map<String,String> form, Map<String,String> headers,String url) throws IOException;

    /**
     * 模拟简单的Get请求
     * @param url 构造后的url
     * @param headers 请求头
     * @return 返回String格式
     * @throws IOException
     */
    String GetReq(String url,Map<String,String> headers) throws IOException;
}
