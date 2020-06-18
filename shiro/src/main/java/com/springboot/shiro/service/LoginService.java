package com.springboot.shiro.service;

import com.springboot.shiro.model.User;

/**
 * create by lcl on 2020/4/17 14:12
 */
public interface LoginService {
    public User login(String username, String password);
}
