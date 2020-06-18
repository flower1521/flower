package com.springboot.shiro.service.impl;

import com.springboot.shiro.mapper.UserMapper;
import com.springboot.shiro.model.User;
import com.springboot.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by lcl on 2020/4/17 11:27
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
