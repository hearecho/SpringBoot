package com.echo.websocket.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * 只有在onopen的时间能够调用httpSession， 这个时间还是用的 http协议
 * @author echo
 */
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request, HandshakeResponse response) {

        System.out.println(request.getHeaders());
        System.out.println(request.getRequestURI());
        System.out.println(request.getHttpSession());
        System.out.println(response.getHeaders());
//        HttpSession httpSession = (HttpSession)request.getHttpSession();
//        if (httpSession==null) {
//            System.out.println("session为null");
//            sec.getUserProperties().put(HttpSession.class.getName(),null);
//
//        } else {
//            sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
//        }
    }
}
