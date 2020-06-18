package com.trans.actional.service.impl;

import com.trans.actional.dao.AccountDao;
import com.trans.actional.model.Account;
import com.trans.actional.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;

/**
 * 添加事务注解，异常的时候能够保证事务的一致性
 * Created by lenovo on 2019/11/23.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
//    @Transactional
    public void transferAccounts(int fromUserId, int toUserId, float account) {
        Account fromUserAccount = accountDao.getOne(fromUserId);
        fromUserAccount.setBalance(fromUserAccount.getBalance() - account);
        accountDao.save(fromUserAccount);//fromUser扣钱

        Account toUserAccount = accountDao.getOne(toUserId);
        toUserAccount.setBalance(toUserAccount.getBalance() + account);
        //假设转账的时候假如出现异常，业务类或业务方法中没有使用@Transactional控制事务，则会出现钱转出了，收钱人没有收到的情况
        int zero = 1/0;//这里我们先把这个异常注释掉，稍后我们再打开
        accountDao.save(toUserAccount);//toUser加钱
    }
}
