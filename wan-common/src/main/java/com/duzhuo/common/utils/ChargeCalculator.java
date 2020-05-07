package com.duzhuo.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: wanhy
 * @date: 2020/4/11 20:42
 */

public class ChargeCalculator {
    /**
     * 免费时长 15分钟
     */
    public static final int FREE_TIME = 15;
    /**
     * 24小时封顶价
     */
    public static final BigDecimal MAX_ONE_DAY_MONEY = new BigDecimal("120");
    /**
     * 入场时间
     */
    private Date enterTime;
    /**
     * 出场时间
     */
    private Date outTime;

    public ChargeCalculator(Date enterTime,Date outTime){
        this.enterTime = enterTime;
        this.outTime = outTime;
    }

    /**
     * 免费结束时间/开始收费时间
     * @return
     */
    public Date getFreeEndTime(){
        return DateUtils.addMinutes(this.enterTime,FREE_TIME);
    }

    public BigDecimal sumMoney(){
        return null;
    }

    /**
     * 阶段1收费
     * @return
     */
    public BigDecimal Stage1(){
        return null;
    }


}
