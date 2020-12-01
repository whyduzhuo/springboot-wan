package com.duzhuo.wansystem.entity.base;

/**
 * @author: wanhy
 * @date: 2019/12/30 11:59
 */

import com.duzhuo.common.core.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 系统监控
 */
@Entity
@Table(name = "T_BASE_MONITOR")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_JL_DAZIWEI_SEQ", allocationSize = 1)
public class SysMonitor extends BaseEntity {
    private String os;//操作系统
    private String cpuUserUsed;//cpu用户使用率
    private String cupSysUsed;//cpu系统使用率
    private String cupFree;//cpu空闲率
    private String memTotal;//内存大小
    private String memUsed;//内存使用大小
    private String memUsedPre;//内存使用率

    private String jvmTotal;//jvm大小
    private String jvmUsed;//jvm已使用大小
    private String jvmUsedPre;//jvm使用率

    private String startTime;//启动时间
    private String runTime;//运行时长
}
