package com.duzhuo.wansystem.service.quartz;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.exception.TaskException;
import com.duzhuo.wansystem.config.quartz.ScheduleConstants;
import com.duzhuo.wansystem.dao.quartz.SysJobDao;
import com.duzhuo.wansystem.entity.quartz.SysJob;
import com.duzhuo.wansystem.quartz.Convert;
import com.duzhuo.wansystem.quartz.CronUtils;
import com.duzhuo.wansystem.quartz.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务调度信息 服务层
 * 
 * @author treebear
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SysJobService extends BaseService<SysJob,Long> {
    @Resource
    private Scheduler scheduler;

    @Resource
    private SysJobDao sysJobDao;

    @Resource
    public void setBaseDao(SysJobDao sysJobDao){
        super.setBaseDao(sysJobDao);
    }


    /**
     * 项目启动时，初始化定时器 
	 * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        scheduler.clear();
        List<SysJob> jobList = super.findAll();
        for (SysJob job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }



    /**
     * 暂停任务
     * 
     * @param jobId 调度信息
     */
    public void stopJob(Long jobId) throws SchedulerException {
        SysJob job = super.find(jobId);
        String jobGroup = job.getJobGroup();
        job.setStatus(SysJob.StatusEnum.停用);
        super.update(job);
        scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }

    /**
     * 恢复任务
     * 
     * @param jobId 调度信息
     */
    public void resumeJob(Long jobId) throws SchedulerException {
        SysJob job = super.find(jobId);
        String jobGroup = job.getJobGroup();
        job.setStatus(SysJob.StatusEnum.启用);
        super.update(job);
        scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param jobId 调度信息
     */
    public void deleteJob(Long jobId) throws SchedulerException {
        SysJob job = super.find(jobId);
        String jobGroup = job.getJobGroup();
        super.delete(jobId);
        scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
    }

    /**
     * 批量删除调度信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public void deleteJobByIds(String ids) throws SchedulerException {
        Long[] jobIds = Convert.toLongArray(ids);
        for (Long jobId : jobIds) {
            deleteJob(jobId);
        }
    }

    /**
     * 任务调度状态修改
     * 
     * @param jobId 调度信息
     */
    public void changeStatus(Long jobId) throws SchedulerException {
        SysJob job = super.find(jobId);
        SysJob.StatusEnum status = job.getStatus();
        if (status== SysJob.StatusEnum.停用) {
            resumeJob(job.getId());
        }
        else if (status== SysJob.StatusEnum.启用) {
            stopJob(job.getId());
        }
    }

    /**
     * 立即运行任务
     * 
     * @param jobId 调度信息
     */
    public void run(Long jobId) throws SchedulerException {
        SysJob tmpObj = super.find(jobId);
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, tmpObj);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, tmpObj.getJobGroup()), dataMap);
    }

    /**
     * 新增任务
     * 
     * @param jobVO 调度信息 调度信息
     */
    public void addData(SysJob jobVO) throws SchedulerException, TaskException {
        super.validation(jobVO);
        this.check(jobVO);
        super.save(jobVO);
        ScheduleUtils.createScheduleJob(scheduler, jobVO);
    }

    public void edit(SysJob jobVO) {
        super.validation(jobVO);
        this.check(jobVO);
        SysJob sysJob = super.find(jobVO.getId());
        sysJob.setInvokeTarget(jobVO.getInvokeTarget());
        sysJob.setCronExpression(jobVO.getCronExpression());
        sysJob.setStatus(jobVO.getStatus());
        sysJob.setMisfirePolicy(jobVO.getMisfirePolicy());
        sysJob.setConcurrent(jobVO.getConcurrent());
        super.update(sysJob);
    }

    private void check(SysJob jobVO){
        Boolean b = CronUtils.isValid(jobVO.getCronExpression());
        if (!b){
            throw new ServiceException("cron表达式有误！");
        }
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param jobVo 调度信息
     */
    public void updateJob(SysJob jobVo) throws SchedulerException, TaskException {
        SysJob job = super.find(jobVo.getId());
        this.check(jobVo);
        job.setCronExpression(jobVo.getCronExpression());
        super.update(job);
        updateSchedulerJob(job, job.getJobGroup());
    }

    /**
     * 更新任务
     * 
     * @param job 任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }


}