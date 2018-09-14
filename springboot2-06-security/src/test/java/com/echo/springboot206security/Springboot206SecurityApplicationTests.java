package com.echo.springboot206security;

import com.echo.springboot206security.bean.UrlAuthBean;
import com.echo.springboot206security.entity.RoleEntity;
import com.echo.springboot206security.entity.UrlEntity;
import com.echo.springboot206security.entity.UserEntity;
import com.echo.springboot206security.repository.RoleRepository;
import com.echo.springboot206security.repository.UrlRepository;
import com.echo.springboot206security.repository.UserRepository;
import com.echo.springboot206security.service.SetUrlAuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot206SecurityApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    SetUrlAuthService setUrlAuthService;

    @Test
    public void contextLoads() {
        String username = "echo";
        UserEntity userEntity = userRepository.findByUsername(username);
        System.out.println(userEntity);
    }

    @Test
    public void testUrlRepository() {
        List<UrlEntity> urlEntities = urlRepository.findAll();
        for (UrlEntity urlEntity: urlEntities) {
            System.out.println(urlEntity.getUrl());
            List<RoleEntity> roleEntityList = new ArrayList<>(urlEntity.getRolelist());
            for (RoleEntity roleEntity:roleEntityList) {
                System.out.println(roleEntity.getRole());
            }
        }
    }

    @Test
    public void testSetUrlAuthService() {
        List<UrlAuthBean> urlAuthBeans = setUrlAuthService.
                getUrlAuthBeanList();
        for (UrlAuthBean urlAuthBean:urlAuthBeans) {
            System.out.println(urlAuthBean.toString());
        }
    }

}
