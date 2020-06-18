package com.trans.actional;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan("com.trans.actional.model")
//@MapperScan("com.trans.actional.mapper")
//@ServletComponentScan(basePackages = "com.trans.actional.listener")
//@EnableJms
@EnableAsync
@EnableScheduling
public class TransactionalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionalApplication.class, args);
	}

}
