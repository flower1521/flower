package com.springboot.shiro.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * create by lcl on 2020/4/17 11:24
 */
@Data
public class Role {
    private Integer rid;
    private String rolename;
    private Set<Permission> permissions = new HashSet<Permission>();
}
