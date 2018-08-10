package com.echo.springboot201jpa.entity;

import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *@Entity User实体类 自动创建数据表
 *
 */
@Entity
@Table(name = "w_user")
@Proxy(lazy = false)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME")
    private Date createTime = Calendar.getInstance(Locale.CHINA).getTime();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "id:\t"+id+"\nusername:\t"+username+
                "\npassword:\t"+password+
                "\ncreated_time:\t"+createTime;
    }
}
