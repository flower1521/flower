package com.trans.actional.controller;

import com.trans.actional.async.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * create by lcl on 2020/1/21 13:44
 */
@RestController
@RequestMapping("/async")
public class AsyncTaskController {
    @Autowired
    private AsyncTask asyncTask;

    @GetMapping("execTask")
    public String execTask() throws InterruptedException {
        long begin = System.currentTimeMillis();

        Future<String> task4 = asyncTask.task4();
        Future<String> task5 = asyncTask.task5();
        Future<String> task6 = asyncTask.task6();

        for (;;) {
            if (task4.isDone() && task5.isDone() && task6.isDone()) {
                break;
            }
        }

        long end = System.currentTimeMillis();
        long total = end - begin;
        System.out.println("执行总耗时=" + total);
        return "execTask success!!!";
    }
}
