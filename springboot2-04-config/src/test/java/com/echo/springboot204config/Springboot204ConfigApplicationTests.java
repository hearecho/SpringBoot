package com.echo.springboot204config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot204ConfigApplicationTests {

	@Autowired
	ApplicationContext ioc;

	@Test
	public void contextLoads() {
	}

	@Test
    public void testHelloService(){
	    //Bean  参数是方法名
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
	}

}
