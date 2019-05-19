package com.echo.websocket.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author hearecho
 * @createtime 2019/5/18 15:43
 */
@Component
@ServerEndpoint(value = "/my-chat/{usernick}")
public class WebSocketController {
    /**
     * 连接事件 加入注解
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam(value = "usernick") String userNick, Session session) {
        String message = "有新游客[" + userNick + "]加入聊天室!";
        System.out.print(message);
        Message resp = new Message();
        resp.setMessage(message);
        WebSocketUtil.addSession(userNick, session);
        //此时可向所有的在线通知 某某某登录了聊天室
        WebSocketUtil.sendMessageForAll(resp);
    }

    @OnClose
    public void onClose(@PathParam(value = "usernick") String userNick,Session session) {
        String message = "游客[" + userNick + "]退出聊天室!";
        System.out.println(message);
        WebSocketUtil.remoteSession(userNick);
        //此时可向所有的在线通知 某某某登录了聊天室
        Message resp = new Message();
        resp.setMessage(message);
        WebSocketUtil.sendMessageForAll(resp);
    }

    @OnMessage
    public void OnMessage(@PathParam(value = "usernick") String userNick, String message) {
        //类似群发
        String info = "游客[" + userNick + "]：" + message;
        System.out.println(info);
        Message resp = new Message();
        resp.setMessage(message);
        resp.setSendRole(userNick);
        WebSocketUtil.sendMessageForAll(resp);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("异常:"+ throwable);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}
