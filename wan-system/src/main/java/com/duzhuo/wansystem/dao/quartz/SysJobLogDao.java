package com.duzhuo.wansystem.dao.quartz;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.quartz.SysJobLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * 调度任务日志信息 数据层
 * 
 * @author treebear
 */
public interface SysJobLogDao extends BaseDao<SysJobLog,Long> {

    /**
     * 清空任务日志
     */
    @Query(value = "DELETE FROM t_sys_job_log",nativeQuery = true)
    @Modifying
    void cleanJobLog();
}
