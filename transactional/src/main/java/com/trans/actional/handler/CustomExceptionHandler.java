package com.trans.actional.handler;

import com.trans.actional.exception.MyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常捕获处理
 * create by lcl on 2020/6/17 15:52
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Map advice(Exception ex) {
        Map map = new HashMap();
        map.put("code", 400);
        if (ex instanceof MissingServletRequestParameterException) {
            map.put("msg", "缺少必需参数："+((MissingServletRequestParameterException) ex).getParameterName());
        } else if (ex instanceof MyException) {
            map.put("msg", "这是自定义异常");
        }
        return map;
    }
}
