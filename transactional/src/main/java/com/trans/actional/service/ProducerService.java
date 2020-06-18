package com.trans.actional.service;

/**
 * Created by lenovo on 2019/12/3.
 */
public interface ProducerService {
    public void sendMsg(String destinationName, String message);
}
