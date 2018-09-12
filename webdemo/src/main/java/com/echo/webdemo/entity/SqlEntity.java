package com.echo.webdemo.entity;

import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@Table(name = "sqlPro")
@Lazy(value = false)
public class SqlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    private long time;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id+"\t"+time+"\t"+title+"\t"+type;
    }
}
