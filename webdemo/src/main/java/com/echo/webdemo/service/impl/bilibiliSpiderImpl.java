package com.echo.webdemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.echo.webdemo.service.DataService;
import com.echo.webdemo.service.bilibiliSpider;
import com.echo.webdemo.utils.DealDate;
import com.echo.webdemo.utils.DealJson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class bilibiliSpiderImpl implements bilibiliSpider {

    DataService dataService = new DataServiceImpl();
    @Override
    public Map<String,String> getUserInfo(int mid) throws IOException {
        Map<String,String> form = new HashMap<>();
        form.put("mid",String.valueOf(mid));
        Map<String,String> headers = new HashMap<>();
        headers.put("Host","space.bilibili.com");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        headers.put("Referer","https://space.bilibili.com/53556317/");
        String url = "https://space.bilibili.com/ajax/member/GetInfo";

        String resu = dataService.PostReq(form,headers,url);
        JSONObject jsonObject =JSONObject.parseObject(resu);
        Map<String,String> map = new HashMap<>();

        JSONObject data = (JSONObject)jsonObject.get("data");
        if(data.getString("name").equals("bilibili")) {
            map.put("error","不存在此用户");
        }else {
            //处理不存在的用户
            JSONObject verify = (JSONObject)data.get("official_verify");
            JSONObject level = (JSONObject)data.get("level_info");

            int regtime = Integer.parseInt(data.getString("regtime"));
            String time = DealDate.TimeStampToString(regtime);
            map.put("regtime",time);
            map.put("birthday",data.getString("birthday"));
            map.put("sex",data.getString("sex"));
            map.put("face",data.getString("face"));
            map.put("name",data.getString("name"));
            map.put("desc",verify.getString("desc"));
            map.put("level",level.getString("current_level"));
            map.put("error","");
        }

        return map;
    }

    @Override
    public Map<String, String> getfollow(int mid) throws IOException {
        Map<String,String> headers = new HashMap<>();
        headers.put("Host","api.bilibili.com");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        String url = "https://api.bilibili.com/x/relation/stat?vmid="+mid;
        String resu = dataService.GetReq(url,headers);

        //处理数据
        JSONObject json = DealJson.StringToJson(resu);
        Map<String,String> resu_map = new HashMap<>();

        JSONObject data = (JSONObject)json.get("data");
        resu_map.put("follower",data.getString("follower"));
        resu_map.put("following",data.getString("following"));

        return resu_map;
    }
}
