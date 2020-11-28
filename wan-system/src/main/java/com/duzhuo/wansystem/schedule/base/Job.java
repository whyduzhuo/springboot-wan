package com.duzhuo.wansystem.schedule.base;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/5 12:16
 */
@Component
public class Job {

    @Scheduled(cron = "0 */3 * * * ?")
    public void test(){
        System.err.println("定时任务测试！");
    }
}
