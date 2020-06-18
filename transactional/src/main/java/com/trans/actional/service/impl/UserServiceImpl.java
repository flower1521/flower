package com.trans.actional.service.impl;

import com.trans.actional.mapper.UserMapper;
import com.trans.actional.model.User;
import com.trans.actional.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lenovo on 2019/11/24.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User sel(int id) {
        return userMapper.sel(id);
    }

    @Override
    @Transactional
    public void add(User user) {
        userMapper.add(user);
    }
}
