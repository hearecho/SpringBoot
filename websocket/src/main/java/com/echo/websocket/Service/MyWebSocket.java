package com.echo.websocket.Service;

import com.alibaba.fastjson.JSONObject;
import com.echo.websocket.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Websocket  最主要的  ServerEndpoint这个注解
 */
@Component
@ServerEndpoint("/webSocket/{homeId}")
public class MyWebSocket {
    //日志
    Logger logger = LoggerFactory.getLogger(getClass());
    //会话
    private Session session;

    //线程安全的集合  Set 存储每一个用户连接的webscoket对象
    private static CopyOnWriteArraySet<MyWebSocket> webSockets = new CopyOnWriteArraySet<>();

    //存储信息的类
    private MessageEntity messageEntity = new MessageEntity();

    /**
     * 有一个新用户连接就开启一个会话， type=1  代表消息是建立新连接的消息
     * 连接成功后设置   1.信息类型Type=1 : 代表建立新连接的消息
     *               2. 设置活跃用户数目 :  就是 Set的大小
     *               3. 设置用户的id 和 name
     *               4. 设置房间的homeId
     *               5. 设置连接时间
     * @param session
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("homeId") Integer homeId) {
        this.session = session;
        webSockets.add(this);
        //获取聊天房间的id
        System.out.println(homeId);
        //测试session
//        System.out.println(session.getBasicRemote());
//        System.out.println(session.getAsyncRemote());
//        System.out.println(session.getContainer());
//        System.out.println(session.getId());   //随着连接数目 从0 开始递增  断开数目并不会减少
//        System.out.println(session.getQueryString());
//        System.out.println(session.getPathParameters());
//        System.out.println(session.getRequestURI());


        //设置发送的数据
        messageEntity.setType(1);
        messageEntity.setUserNum(webSockets.size());
        messageEntity.setHomeId(homeId);
        messageEntity.setDate(System.currentTimeMillis());
        messageEntity.setMessage("有新的连接");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageEntity);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        this.sendMessage(Json);
        logger.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
    }

    /**
     * 当一个会话关闭的时间;  type=2 代表消息是 关闭连接的信息
     */
    @OnClose
    public void onClose() {
        //从会话集合中删去
        webSockets.remove(this);

        messageEntity.setType(2);
        messageEntity.setUserNum(webSockets.size());
        messageEntity.setMessage("有用户离开");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageEntity);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        this.sendMessage(Json);


        logger.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
    }

    /**
     * 接收来客户端的信息
     * @param message  type=3  代表是发送消息的信息
     */
    @OnMessage
    public void onMessage(String message) {

        messageEntity.setType(3);
        messageEntity.setUserNum(webSockets.size());
        //可以选择接收json字符串 处理json字符串 取出里面的信息
        JSONObject jsonObject = JSONObject.parseObject(message);
        messageEntity.setMessage(jsonObject.getString("message"));
        messageEntity.setUsername(jsonObject.getString("userId"));

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageEntity);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        this.sendMessage(Json);

        logger.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    public void sendMessage(String message) {
        for (MyWebSocket webSocket : webSockets) {
            logger.info("【websocket消息】广播消息, message={}", message);
            //给每一个 session 发送消息

            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
