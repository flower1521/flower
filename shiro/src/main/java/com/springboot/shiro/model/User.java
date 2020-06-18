package com.springboot.shiro.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * create by lcl on 2020/4/17 11:21
 */
@Data
public class User {
    private Integer uid;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<Role>();
}
