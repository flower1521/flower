package com.trans.actional.controller;

import com.trans.actional.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2019/11/23.
 */
@Api("账户控制器类")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "转账", notes = "测试事务")
    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String transferAccounts() {
        try {
            //1号zhangsan 给2号lisi 转账200元
            accountService.transferAccounts(1, 2, 200);
            return "ok";
        } catch (Exception e) {
            return "no";
        }
    }
}
