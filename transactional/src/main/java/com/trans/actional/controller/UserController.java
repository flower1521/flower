package com.trans.actional.controller;

import com.trans.actional.model.User;
import com.trans.actional.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lenovo on 2019/11/24.
 */
@RestController
@RequestMapping("/testBoot")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable int id) {
        return userService.sel(id).toString();
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String addUser(@RequestBody User user) {
        try {
            userService.add(user);
            logger.info("添加用户成功");
        } catch (Exception e) {
            logger.error("添加用户失败");
            e.printStackTrace();
        }
        return "success";
    }
}
