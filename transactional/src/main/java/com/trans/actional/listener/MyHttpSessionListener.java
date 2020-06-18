package com.trans.actional.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by lenovo on 2019/11/30.
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    public static int online = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("创建session");
        online++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("销毁session");
    }
}
