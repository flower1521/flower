package com.trans.actional.exception;

import lombok.Data;

/**
 * create by lcl on 2020/6/17 15:47
 */
@Data
public class MyException extends RuntimeException {
    private long code;
    private String msg;

    public MyException(Long code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public MyException(String msg){
        super(msg);
        this.msg = msg;
    }
}
