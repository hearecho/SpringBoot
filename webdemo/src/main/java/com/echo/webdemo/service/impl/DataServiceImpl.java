package com.echo.webdemo.service.impl;

import com.echo.webdemo.service.DataService;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.Map;

public class DataServiceImpl implements DataService{

    @Override
    public String PostReq(Map<String, String> form, Map<String, String> headers,String url) throws IOException {
        URL post_url = new URL(url);
        HttpURLConnection connection=(HttpURLConnection)post_url.openConnection();

        //配置参数
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        //配置请求头
        Iterator<String> iterheaders = headers.keySet().iterator();
        Iterator<String> iterform = form.keySet().iterator();
        String key,value;
        while(iterheaders.hasNext()) {
            key = iterheaders.next();
            value = headers.get(key);
//            System.out.println(key+" : "+value);
            connection.setRequestProperty(key,value);
        }
        //开始连接
        connection.connect();
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //写入表单
        StringBuffer post_form = new StringBuffer();
        while(iterform.hasNext()) {
            key = iterform.next();
            value = form.get(key);
//            System.out.println(key+" : "+value);
            post_form.append(key+"="+ URLEncoder.encode(value,"utf-8")+"&");
        }
        out.writeBytes(post_form.toString());
        out.flush();
        out.close();
        //获取结果
        StringBuffer buffer = new StringBuffer();
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line="";
        while((line=buff.readLine())!=null) {
            buffer.append(line);
        }
//        System.out.println(buffer.toString());
        buff.close();
        //断开连接
        connection.disconnect();
        return buffer.toString();
    }

    @Override
    public String GetReq(String url, Map<String, String> headers) throws IOException {
        URL get_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection)get_url.openConnection();

        Iterator<String> iterheaders = headers.keySet().iterator();
        String key,value;
        while(iterheaders.hasNext()) {
            key = iterheaders.next();
            value = headers.get(key);
//            System.out.println(key+" : "+value);
            connection.setRequestProperty(key,value);
        }

        connection.connect();
        StringBuffer buffer = new StringBuffer();
        BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line="";
        while((line=buff.readLine())!=null) {
            buffer.append(line);
        }
        System.out.println(buffer.toString());
        buff.close();
        //断开连接
        connection.disconnect();
        return buffer.toString();
    }
}
