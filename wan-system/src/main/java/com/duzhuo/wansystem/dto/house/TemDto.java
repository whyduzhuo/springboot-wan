package com.duzhuo.wansystem.dto.house;

import oracle.jdbc.driver.DatabaseError;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: wanhy
 * @date: 2020/8/22 12:48
 */

public class TemDto implements Comparable<TemDto>{
    private String date;
    private Integer num;

    public TemDto(String date, Integer num) {
        this.date = date;
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public int compareTo(@NotNull TemDto o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date =simpleDateFormat.parse(this.date);
            Date oDate=simpleDateFormat.parse(o.getDate());
            if (date.after(oDate)){
                return 1;
            }else if (date.before(oDate)){
                return -1;
            }else {
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
