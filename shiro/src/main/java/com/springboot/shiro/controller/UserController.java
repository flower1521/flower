package com.springboot.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by lcl on 2020/4/17 11:50
 */
@RestController
public class UserController {
    //用户登录
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //把前端输入的username和password封装为token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "login success";
        } catch (Exception e) {
            return "没有权限";
        }
    }

//    @RequiresPermissions("employee:work")
    @RequiresPermissions("work")
    @ResponseBody
    @RequestMapping("/show")
    public String showInfo() {
        return "这是要看的信息";
    }
}
