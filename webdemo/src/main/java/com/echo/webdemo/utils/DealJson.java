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

    public static Map<String,Object> DealJson(String str) {
        Map<String,Object> map = new HashMap<>();
        String json_str = "{\"status\":true,\"data\":{\"mid\":493199," +
                "\"name\":\"\\u88ab\\u4e00\\u8840\\u4e86\",\"sex\":\"\\u7537\"," +
                "\"rank\":10000,\"face\":\"http:\\/\\/i1.hdslb.com\\/bfs\\/face\\/3e09f542eba204e19c91d0f99f0e3a841a880f08.jpg\"," +
                "\"regtime\":1344500978,\"spacesta\":0,\"birthday\":\"02-04\",\"sign\":\"\",\"level_info\":{\"current_level\":5},\"official_verify\":{\"type\":-1,\"desc\":\"\"," +
                "\"suffix\":\"\"},\"vip\":{\"vipType\":2,\"vipStatus\":1}," +
                "\"toutu\":\"bfs\\/space\\/768cc4fd97618cf589d23c2711a1d1a729f42235.png\"," +
                "\"toutuId\":1,\"theme\":\"default\",\"theme_preview\":\"\",\"coins\":0," +
                "\"im9_sign\":\"bfeaa33a52bf2c358d20c1a33b8b7260\",\"fans_badge\":false}}\n";

        JSONObject jsonObject =JSONObject.parseObject(json_str);

        JSONObject data = (JSONObject)jsonObject.get("data");
        JSONObject verify = (JSONObject)data.get("official_verify");
        JSONObject level = (JSONObject)data.get("level_info");
        System.out.println(data);
        map.put("regtime",data.getString("regtime"));
        map.put("birthday",data.getString("birthday"));
        map.put("sex",data.getString("sex"));
        map.put("face",data.getString("face"));
        map.put("name",data.getString("name"));
        map.put("desc",verify.getString("desc"));
        map.put("level",level.getString("current_level"));

        System.out.println(map.toString());
        return map;
    }


}
