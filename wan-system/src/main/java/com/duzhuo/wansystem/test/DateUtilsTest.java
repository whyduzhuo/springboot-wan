package com.duzhuo.wansystem.test;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/5/11 22:20
 */

public class DateUtilsTest {
    public static void main(String[] args) {
        try {
            DateUtils.parseDate("20:00","HH:m0");
            System.err.println("success");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
