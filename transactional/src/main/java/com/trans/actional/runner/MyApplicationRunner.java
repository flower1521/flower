package com.trans.actional.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2019/12/2.
 */
@Component
@Order(2)
public class MyApplicationRunner implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("MyApplicationRunner 项目启动时执行...");
    }
}
