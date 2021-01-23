package com.duzhuo.wansystem.service.quartz;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.wansystem.dao.quartz.SysJobLogDao;
import com.duzhuo.wansystem.entity.quartz.SysJobLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 定时任务调度日志信息 服务层
 * 
 * @author treebear
 */
@Service
public class SysJobLogService extends BaseService<SysJobLog,Long> {
    @Resource
    private SysJobLogDao sysJobLogDao;

    @Resource
    public void setBaseDao(SysJobLogDao sysJobLogDao){
        super.setBaseDao(sysJobLogDao);
    }


    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    public void addJobLog(SysJobLog jobLog) {
        super.save(jobLog);
    }


    /**
     * 清空任务日志
     */
    public void cleanJobLog()
    {
        sysJobLogDao.cleanJobLog();
    }
}
