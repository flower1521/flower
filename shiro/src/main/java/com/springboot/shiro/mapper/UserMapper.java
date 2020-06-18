package com.springboot.shiro.mapper;

import com.springboot.shiro.model.User;
import org.springframework.stereotype.Repository;

/**
 * create by lcl on 2020/4/17 11:26
 */
@Repository
public interface UserMapper {
    User findByUsername(String username);
}
