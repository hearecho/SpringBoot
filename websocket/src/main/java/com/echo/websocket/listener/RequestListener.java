package com.echo.websocket.listener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author echo
 * 失败 这样的处理方法可以得到 Session不是前端的那个 而是自己传过来的;
 *
 */
//@Component
public class RequestListener implements ServletRequestListener {
    public RequestListener() {
        super();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        //将所有的请求都带上httpsession
        HttpSession httpSession= ((HttpServletRequest) servletRequestEvent.getServletRequest()).getSession();
    }
}
