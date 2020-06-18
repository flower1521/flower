package com.trans.actional.service.impl;

import com.trans.actional.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

/**
 * Created by lenovo on 2019/12/3.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private static Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Override
    @JmsListener(destination = "test.queue")
    public void receiveMsg(String text) {
        logger.info("<<<====== 收到消息 {}", text);
    }

    @Override
    //使用JmsListener配置消费者监听的队列，其中name是接收到的消息
    @JmsListener(destination = "ActiveMQQueue")
    //SendTo会将此方法返回的数据，写入到OutQueue中去
    @SendTo("SQueue")
    public String handleMessage(String name) {
        logger.info("成功接收Name：" + name);
        return "成功接收Name：" + name;
    }
}
