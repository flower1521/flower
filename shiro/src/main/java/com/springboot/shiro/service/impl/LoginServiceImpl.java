package com.springboot.shiro.service.impl;

import com.springboot.shiro.mapper.UserMapper;
import com.springboot.shiro.model.User;
import com.springboot.shiro.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * create by lcl on 2020/4/17 14:12
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }
        User user = userMapper.findByUsername(username);
        if (user != null) {
            //校验密码是否正确
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
