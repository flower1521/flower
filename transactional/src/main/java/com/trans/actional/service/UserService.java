package com.trans.actional.service;

import com.trans.actional.model.User;

/**
 * Created by lenovo on 2019/11/24.
 */
public interface UserService {
    public User sel(int id);

    public void add(User user);
}
