package com.echo.webdemo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DealJson {


    public static Map<String,String> StringToMap(String str) {
        Map<String,String> maps = (Map) JSON.parse(str);

        return maps;
    }

    public static JSONObject StringToJson(String str) {
        JSONObject jsonObject =JSONObject.parseObject(str);
        return jsonObject;
    }



}
