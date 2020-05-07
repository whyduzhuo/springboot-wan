package com.duzhuo.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    /**
     * 反射测试
     * @param clazz
     * @param methodName
     * @param args
     */
    public static void ceshi(Class<?> clazz, String methodName,Object[] args){
        try {
            // 获取类的示例对象
            Object o = clazz.newInstance();
            // 参数类型列表
            Class[] classes = new Class[args.length];
            for (int i=0;i<args.length;i++) {
                classes[i] = args[i].getClass();
            }
            // 获取方法
            Method method = clazz.getMethod(methodName,classes);

            // 执行方法
            Object result = method.invoke(o,args);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}