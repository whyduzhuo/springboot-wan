package com.duzhuo.wansystem.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: wanhy
 * @date: 2020/6/19 10:48
 */

public class Mytest {


    /**
     * 题目一：遍历data
     * key值可以直接使用
     * 要求输出结果
     * 张三：12
     * 李四：13
     * @param args
     */
    public static void main(String[] args){

        List<Map<String,String>> data= new ArrayList<>();

        Map<String,String> zs = new HashMap<>(2);
        zs.put("name","张三");
        zs.put("age","12");
        data.add(zs);

        Map<String,String> lx = new HashMap<>(2);
        lx.put("name","李四");
        lx.put("age","13");
        data.add(lx);



    }


    //题目二写数据库sql
    //表名：T_ADMIN
    //两个字段：USERNAME,PWD
    //表中如果USERNAME和PWD都相同的数据为重复数据
    //要求查出重复数据和重复次数
    //ege：张三，111，3
    //

    //SrpingMVC注解，
    //异常类，不用抛。
    //事务传播行为，隔离级别，rollbackFor,使得事务不生效
    //数据库约束


}
