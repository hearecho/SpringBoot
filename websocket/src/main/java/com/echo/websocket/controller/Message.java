package com.echo.websocket.controller;

import java.io.Serializable;

/**
 * @author hearecho
 * @createtime 2019/5/18 15:47
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 15154L;

    private String sendRole;
    private String receRole;
    private String message;

    public void setSendRole(String role) {
        this.sendRole = role;
    }

    public void setReceRole( String role) {
        this.receRole = role;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendRole() {
        return this.sendRole;
    }

    public String getReceRole( ) {
        return this.receRole;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        String resp = "{ \"sendRole\":"+sendRole+ ",\"message\":"+message+",\"receRole\":"+receRole+"}";
        return resp;
    }
}
