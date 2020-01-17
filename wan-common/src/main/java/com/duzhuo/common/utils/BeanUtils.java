package com.duzhuo.common.utils;

/**
 * @author: wanhy
 * @date: 2020/1/4 11:56
 */

public class BeanUtils  extends org.springframework.beans.BeanUtils{
    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
