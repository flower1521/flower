package com.trans.actional.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * 监听器是一个专门用于对其他对象身上发生的事件或状态改变进行监听和相应处理的对象，当被监视的对象发生情况时，
 * 立即采取相应的行动。
 * Created by lenovo on 2019/11/30.
 */
@WebListener
public class RequestListenter implements ServletRequestListener {
    private static Logger logger = LoggerFactory.getLogger(RequestListenter.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        logger.info("-------------请求销毁-------------");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        logger.info("-------------请求创建-------------");
    }
}
