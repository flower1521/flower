package com.trans.actional.service;

/**
 * Created by lenovo on 2019/12/3.
 */
public interface ConsumerService {
    public void receiveMsg(String text);

    public String handleMessage(String name);
}
