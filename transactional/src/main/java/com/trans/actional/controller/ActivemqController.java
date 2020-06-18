package com.trans.actional.controller;

import com.trans.actional.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2019/12/3.
 */
@RestController
public class ActivemqController {
    @Autowired
    private ProducerService producerService;

    @RequestMapping(value = "/activemq/producer", method = RequestMethod.GET)
    public String producer() {
        for (int i = 0; i < 10; i++) {
            producerService.sendMsg("test.queue", "Queue message " + i);
        }
        return "ok";
    }
}
