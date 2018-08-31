package com.echo.webdemo;

import com.echo.webdemo.entity.UserEntity;
import com.echo.webdemo.repository.UserRepository;
import com.echo.webdemo.service.DataService;
import com.echo.webdemo.service.impl.DataServiceImpl;
import com.echo.webdemo.utils.DealDate;
import com.echo.webdemo.utils.DealJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebdemoApplicationTests {

	@Autowired
	UserRepository userRepository;
	DataService dataService=new DataServiceImpl();
	@Test
	public void contextLoads() {
        UserEntity userEntity = userRepository.getOne(1);
        System.out.println(userEntity.toString());
	}


	@Test
    public void testPost() throws IOException {
        Map<String,String> form = new HashMap<>();
        form.put("mid","493199");
        Map<String,String> headers = new HashMap<>();
        headers.put("Host","space.bilibili.com");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        headers.put("Referer","https://space.bilibili.com/53556317/");
        String url = "https://space.bilibili.com/ajax/member/GetInfo";

        String resu = dataService.PostReq(form,headers,url);
        System.out.println(resu);
        Map<String,String> resu_map = new HashMap<>();
        //处理数据
        Map<String,String> info = DealJson.StringToMap(resu);
        Object data = info.get("data");
        Map<String,String> re_info = DealJson.StringToMap(data.toString());

        for (Object map : re_info.entrySet()){
            System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());
        }
    }
    @Test
    public void testGet() throws IOException {
        Map<String,String> headers = new HashMap<>();
        headers.put("Host","api.bilibili.com");
        headers.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        String url = "https://api.bilibili.com/x/relation/stat?vmid=17171565";
        String resu = dataService.GetReq(url,headers);
        Map<String,String> info = DealJson.StringToMap(resu);
        Object data = info.get("data");
        Map<String,String> re_info = DealJson.StringToMap(data.toString());
        for (Object map : re_info.entrySet()){
            System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());
        }
    }

    @Test
    public void testJosn() {
	    DealJson.DealJson("");
    }
    @Test
    public void testDate() {
        String date = DealDate.TimeStampToString(1344500978L);
        System.out.println(date);
    }

}
