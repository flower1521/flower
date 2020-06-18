package com.trans.actional.mapper;

import com.trans.actional.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2019/11/24.
 */
@Repository
public interface UserMapper {
    User sel(int id);

    void add(User user);
}
