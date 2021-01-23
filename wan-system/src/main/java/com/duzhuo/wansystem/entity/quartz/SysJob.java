package com.duzhuo.wansystem.entity.quartz;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.wansystem.config.quartz.ScheduleConstants;
import com.duzhuo.wansystem.quartz.CronUtils;
import com.duzhuo.wansystem.service.quartz.SysJobService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务调度表 sys_job
 * 
 * @author treebear
 */
@Data
@Entity
@ApiModel(value = "定时任务调度表")
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_sys_job")
@Unique(service = SysJobService.class,uniqueColumns = {@UniqueColumn("jobName"),@UniqueColumn("jobGroup")},message = "任务名称重复")
public class SysJob extends BaseEntity implements Serializable {
    public enum LogicalEnum{
        默认,
        立即触发执行,
        触发一次执行,
        不触发立即执行,
    }
    public enum StatusEnum{
        启用,
        停用,
    }
    public enum ConcurrentEnum{
        允许,
        禁止
    }

    private static final long serialVersionUID = 850472446679825498L;

    @ApiModelProperty(value = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    @Size(max = 64, message = "任务名称不能超过64个字符")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    @NotBlank(message = "任务组名不能为空")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串")
    @NotBlank(message = "调用目标字符串不能为空")
    @Size(max = 1000, message = "调用目标字符串长度不能超过500个字符")
    private String invokeTarget;

    @ApiModelProperty(value = "cron执行表达式")
    @NotBlank(message = "Cron执行表达式不能为空")
    @Size(max = 255, message = "Cron执行表达式不能超过255个字符")
    private String cronExpression;

    @ApiModelProperty(value = "cron计划策略")
    private LogicalEnum misfirePolicy = LogicalEnum.默认;

    @NotNull(message = "是否允许并发执行")
    @ApiModelProperty(value = "是否并发执行")
    private ConcurrentEnum concurrent;

    @ApiModelProperty(value = "任务状态")
    @NotNull(message = "任务状态不能为空")
    private StatusEnum status;


    /**
     * 下一次执行的时间
     * @return
     */
    public Date getNextValidTime() {
        if (StringUtils.isNotEmpty(cronExpression)) {
            return CronUtils.getNextExecution(cronExpression);
        }
        return null;
    }

    public String getStatusHtml(){
        if (status==StatusEnum.启用){
            return "<span  class=\"label label-success\">已启用</span>";
        }
        if (status==StatusEnum.停用){
            return "<span  class=\"label label-danger\">已暂停</span>";
        }
        return "";
    }

}