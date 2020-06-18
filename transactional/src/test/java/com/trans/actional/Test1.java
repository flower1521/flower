package com.trans.actional;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lenovo on 2019/12/4.
 */
public class Test1 {
    @Test
    public void test01() {
        String sql = "INSERT INTO user(userName, passWord, realName) VALUES (?, ?, ?)";
        String REGEX = ".*insert\\\\u0020.*|.*delete\\\\u0020.*|.*update\\\\u0020.*";
        if (sql.matches(REGEX)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        BlockingQueue queue = new ArrayBlockingQueue(2);
    }
}
