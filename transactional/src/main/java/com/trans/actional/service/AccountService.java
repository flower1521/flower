package com.trans.actional.service;

/**
 * Created by lenovo on 2019/11/23.
 */
public interface AccountService {
    /**
     * 转账
     * @param fromUserId
     * @param toUserId
     * @param account
     */
    public void transferAccounts(int fromUserId,int toUserId,float account);
}
