package com.trans.actional.service.impl;

import com.trans.actional.service.ProducerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * Created by lenovo on 2019/12/3.
 */
@Service
public class ProducerServiceImpl implements ProducerService {
    private static Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public void sendMsg(String destinationName, String message) {
        logger.info("======>>> 发送queue消息 {}", message);
        Destination destination = new ActiveMQQueue(destinationName);
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
