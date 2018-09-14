package com.echo.springboot206security.service;

import com.echo.springboot206security.bean.UrlAuthBean;
import com.echo.springboot206security.entity.RoleEntity;
import com.echo.springboot206security.entity.UrlEntity;
import com.echo.springboot206security.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomSecurityConfig 自定义设置 提供一些取值服务
 */
@Service
public class SetUrlAuthService {
    @Autowired
    UrlRepository urlRepository;

    private List<UrlAuthBean> urlAuthBeanList = new ArrayList<>();


    public List<UrlAuthBean> getUrlAuthBeanList() {
        List<UrlEntity> urlEntities = urlRepository.findAll();
        for (UrlEntity urlEntity: urlEntities) {
            UrlAuthBean urlAuthBean = new UrlAuthBean();
            urlAuthBean.setUrl(urlEntity.getUrl());

            List<RoleEntity> roleEntityList = new ArrayList<>(urlEntity.getRolelist());
            List<String> roles = new ArrayList<>();
            for (RoleEntity roleEntity:roleEntityList) {
                roles.add(roleEntity.getRole());
            }
            urlAuthBean.setRols(roles);
            urlAuthBeanList.add(urlAuthBean);
        }
        return urlAuthBeanList;
    }

    public void setUrlAuthBeanList(List<UrlAuthBean> urlAuthBeanList) {
        this.urlAuthBeanList = urlAuthBeanList;
    }

}
