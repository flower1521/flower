package com.trans.actional.controller;

import com.trans.actional.model.User;
import com.trans.actional.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

/**
 * Created by lenovo on 2019/11/24.
 */
@Api("测试控制器类")
@RestController
public class HelloWorldController {
    private static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "你好", notes = "测试")
    @RequestMapping(value = "/hello", method = RequestMethod.GET) //提供路由信息，”/“路径的HTTP Request都会被映射到sayHello方法进行处理。
    public String sayHello(){
        logger.info("hello world");
        if (logger.isDebugEnabled()) {
            logger.debug("debug hello world");
        }
//        redisUtil.addKey("work", "develop", 1000l, TimeUnit.SECONDS);
        String token = null;
        User user = null;
        String lockKey = "testKey";
        try {
            token = redisUtil.tryLock(lockKey, 100L);
            if (!StringUtils.isEmpty(token)) {
                user = new User();
                user.setId(1);
                user.setUserName("lcl");
                user.setRealName("lcl");
                user.setPassWord("123456");
                System.out.println(user.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Bye";
        } finally {
            if (user != null && token != null) {
                redisUtil.unlock(lockKey, token);
            }
        }

        /*RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "lcl");
        String getUrl = "http://USERSERVICE/user?name={name}";
        //get请求
        ResponseEntity<String> getResponseEntity = restTemplate.getForEntity(getUrl, String.class, params);

        String postUrl = "http://USER-SERVICE/user";
        //post请求
        ResponseEntity<String> postResponseEntity = restTemplate.postForEntity(postUrl, user, String.class);
        String body = postResponseEntity.getBody();*/

        return "Hello,World!";
    }

    @GetMapping(value = "/pipeline")
    public void pipeline() {
//        redisUtil.pipeline();
        redisUtil.pipeline2();
    }

    @GetMapping(value = "/single")
    public void single() {
        for (int i = 0; i < 1000; i++) {
            redisUtil.set("key_" + i, "value_" + i);
        }
    }

    @GetMapping(value = "/scan")
    public void scan() {
        String pattern = "name*";
        Consumer<byte[]> consumer = new Consumer<byte[]>() {
            @Override
            public void accept(byte[] bytes) {
                System.out.println(new String(bytes));
            }
        };
        long count = 3;
        redisUtil.scan(pattern, consumer, count);
    }
}
