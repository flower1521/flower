package com.trans.actional.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2019/12/2.
 */
@Component
@Order(1) //如果有多个要执行的方法 @Order 这个注释来规定执行的先后顺序，数字越小优先级越高
public class MyRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(MyRunner.class);

    @Override
    public void run(String... strings) throws Exception {
        logger.info("MyRunner 项目启动时执行...");
    }
}
