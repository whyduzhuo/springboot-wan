package com.duzhuo.common.monitor;


import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 內存相关信息
 * 
 * @author wanhy
 */
public class Mem {
    /**
     * 内存总量
     */
    private BigDecimal total;

    /**
     * 已用内存
     */
    private BigDecimal used;

    /**
     * 剩余内存
     */
    private BigDecimal free;

    public BigDecimal getTotal() {
        return total.divide(new BigDecimal(1024*1024*1024),2,RoundingMode.HALF_UP);
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getUsed() {
        return used.divide(new BigDecimal(1024 * 1024 * 1024),2,RoundingMode.HALF_UP);
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public BigDecimal getFree() {
        return free.divide(new BigDecimal(1024 * 1024 * 1024),2,RoundingMode.HALF_UP);
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getUsage() {
        return used.divide(total,4,RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    }
}
