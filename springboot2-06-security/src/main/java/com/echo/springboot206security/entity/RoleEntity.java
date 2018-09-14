package com.echo.springboot206security.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@Proxy(lazy = false)
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;

    @ManyToMany(mappedBy = "rolelist",fetch = FetchType.EAGER)
    private Set<UserEntity> userList;

    @ManyToMany(mappedBy = "rolelist",fetch = FetchType.EAGER)
    private Set<UrlEntity> urlList;


    public RoleEntity() {
    }

    public RoleEntity(String role, Set<UserEntity> userList, Set<UrlEntity> urlList) {
        this.role = role;
        this.userList = userList;
        this.urlList = urlList;
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(Set<UserEntity> userList) {
        this.userList = userList;
    }

    public Set<UrlEntity> getUrlList() {
        return urlList;
    }

    public void setUrlList(Set<UrlEntity> urlList) {
        this.urlList = urlList;
    }
}
