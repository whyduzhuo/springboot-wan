package com.duzhuo.wansystem.test;

import org.apache.shiro.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
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
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey deskey = keygen.generateKey();
        System.out.println(Base64.encodeToString(deskey.getEncoded()));

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
