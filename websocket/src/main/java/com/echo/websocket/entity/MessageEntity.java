package com.echo.websocket.entity;


public class MessageEntity {
    /**
     *
     */
    private Integer userNum;
    /**
     *
     */
    private Integer type;
    /**
     *
     */
    private Integer homeId;
    /**
     *
     */
    private String message;
    /**
     *  时间戳毫秒
     */
    private long date;
    /**
     *
     */
    private String username = "echo";

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
