package com.springboot.shiro;

import com.springboot.shiro.model.Permission;
import com.springboot.shiro.model.Role;
import com.springboot.shiro.model.User;
import com.springboot.shiro.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
        User user = userService.findByUsername("tom");
        Set<Role> roleSet = user.getRoles();
        for (Role role : roleSet) {
            Set<Permission> permissionSet = role.getPermissions();
            for (Permission permission : permissionSet) {
                System.out.println(permission.getPermissionname());
            }
            System.out.println(role.getRolename());
        }
    }
}
