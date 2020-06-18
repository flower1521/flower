package com.trans.actional.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by lcl on 2020/1/21 14:49
 */
@Component
public class Jobs {
    public static final long ONE_Minute = 60 * 1000;

    @Scheduled(fixedDelay = ONE_Minute)
    public void fixedDelayJob() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " >> fixedDelayJob 执行");
    }

    @Scheduled(fixedRate = ONE_Minute)
    public void fixedRateJob() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " >> fixedRateJob 执行");
    }

    @Scheduled(cron = "0 15 3 * * ?")
    public void cronJob() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " >> cronJob 执行");
    }
}
