package com.trans.actional.dao;

import com.trans.actional.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lenovo on 2019/11/23.
 */
@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {
}
