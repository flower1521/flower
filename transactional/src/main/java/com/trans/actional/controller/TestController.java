package com.trans.actional.controller;

import com.trans.actional.exception.MyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by lcl on 2020/6/17 15:36
 */
@RestController
public class TestController {
    @RequestMapping("testException")
    public String testException() throws Exception{
        throw new MissingServletRequestParameterException("name", "String");
    }

    @RequestMapping("testMyException")
    public String testMyException() throws MyException {
        throw new MyException("i am a myException");
    }
}
