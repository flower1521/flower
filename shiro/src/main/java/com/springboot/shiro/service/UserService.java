package com.springboot.shiro.service;

import com.springboot.shiro.model.User;

/**
 * create by lcl on 2020/4/17 11:27
 */
public interface UserService {
    public User findByUsername(String username);
}
