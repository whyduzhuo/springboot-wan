package com.duzhuo.wansystem.entity.quartz;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 定时任务调度日志表 sys_job_log
 * 
 * @author treebear
 */
@Data
@Entity
@ApiModel(value = "定时任务调度表")
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_sys_job_log")
public class SysJobLog extends BaseEntity {

    private static final long serialVersionUID = -1251294241723447154L;

    @ManyToOne
    @JoinColumn(name = "JOB_ID")
    private SysJob sysJob;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务组名")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串")
    private String invokeTarget;

    @ApiModelProperty(value = "日志信息")
    private String jobMessage;

    @ApiModelProperty(value = "执行状态（0正常 1失败）")
    private String status;

    @ApiModelProperty(value = "异常信息")
    private String exceptionInfo;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    public String getStatusHtml(){
        if ("0".equals(status)){
            return "<span  class=\"label label-success\">成功</span>";
        }
        if ("1".equals(status)){
            return "<span  class=\"label label-danger\">失败</span>";
        }
        return "";
    }
}
