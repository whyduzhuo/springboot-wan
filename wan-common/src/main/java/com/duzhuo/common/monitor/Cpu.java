package com.duzhuo.common.monitor;



import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CPU相关信息
 * 
 * @author wanhy
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private BigDecimal total;

    /**
     * CPU系统使用率
     */
    private BigDecimal sys;

    /**
     * CPU用户使用率
     */
    private BigDecimal used;

    /**
     * CPU当前等待率
     */
    private BigDecimal wait;

    /**
     * CPU当前空闲率
     */
    private BigDecimal free;

    public int getCpuNum()
    {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public BigDecimal getTotal() {
        return total.setScale(4,RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSys() {
        return sys.divide(total,4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public void setSys(BigDecimal sys) {
        this.sys = sys;
    }

    public BigDecimal getUsed() {
        return used.divide(total,4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public BigDecimal getWait() {
        return wait.divide(total,4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public void setWait(BigDecimal wait) {
        this.wait = wait;
    }

    public BigDecimal getFree() {
        return free.divide(total,4, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }
}
