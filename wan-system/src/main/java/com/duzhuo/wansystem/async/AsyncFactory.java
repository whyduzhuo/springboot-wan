package com.duzhuo.wansystem.async;

import com.duzhuo.common.utils.SpringUtils;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import com.duzhuo.wansystem.service.base.SysOperLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 * 
 * @author liuhulu
 *
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger(AsyncFactory.class);

    /**
     * 生成任务
     * 操作日志记录
     * @param sysOperLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(SysOperLog sysOperLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(SysOperLogService.class).addData(sysOperLog);
            }
        };
    }

}
