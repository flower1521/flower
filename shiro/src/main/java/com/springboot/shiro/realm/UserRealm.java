package com.springboot.shiro.realm;

import com.springboot.shiro.model.Permission;
import com.springboot.shiro.model.Role;
import com.springboot.shiro.model.User;
import com.springboot.shiro.service.LoginService;
import com.springboot.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * create by lcl on 2020/4/17 14:08
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    //权限认证，即登录过后，每个身份不一定，对应的所能看的页面也不一样。
    //进行权限验证时，会先执行该方法，获取用户的权限，然后进行权限校验，有权限则继续执行，无权限则提示没有权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        User loginUser = (User) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        User user = userService.findByUsername(loginUser.getUsername());
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRolename());
            //添加权限
            for (Permission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermissionname());
            }
        }
        /*Set<String> stringSet = new HashSet<String>();
        stringSet.add("employee:work");
        simpleAuthorizationInfo.setStringPermissions(stringSet);*/
        return simpleAuthorizationInfo;
    }

    //执行登录操作subject.login(token)时执行
    //身份认证。即登录通过账号和密码验证登陆人的身份信息。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        User user = loginService.login(username, password);
        if (user == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    //清理缓存权限
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
