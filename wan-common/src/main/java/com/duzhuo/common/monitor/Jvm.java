package com.duzhuo.common.monitor;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * JVM相关信息
 * 
 * @author wanhy
 */
public class Jvm {
    /**
     * 当前JVM占用的内存总数(M)
     */
    private BigDecimal total;

    /**
     * JVM最大可用内存总数(M)
     */
    private BigDecimal max;

    /**
     * JVM空闲内存(M)
     */
    private BigDecimal free;

    /**
     * JDK版本
     */
    private String version;

    /**
     * JDK路径
     */
    private String home;

    public BigDecimal getTotal() {
        return total.divide(new BigDecimal(1024*1024),2, RoundingMode.HALF_UP);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getMax() {
        return max.divide(new BigDecimal(1024*1024),2, RoundingMode.HALF_UP);
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getFree() {
        return free.divide(new BigDecimal(1024*1024),2, RoundingMode.HALF_UP);
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getUsed() {
        return total.subtract(free).divide(new BigDecimal(1024*1024),2, RoundingMode.HALF_UP);
    }

    public BigDecimal getUsage() {
        return total.subtract(free).divide(total,4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
    }

    /**
     * 获取JDK名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    /**
     * JDK启动时间
     */
    public String getStartTime() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date= new Date(time);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * JDK运行时间
     */
    public String getRunTime() {
        Date now = new Date();
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date= new Date(time);
        return DurationFormatUtils.formatDuration(now.getTime()-date.getTime(),"d天H小时m分钟");
    }
}
