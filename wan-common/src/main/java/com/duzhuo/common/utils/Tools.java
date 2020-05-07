package com.duzhuo.common.utils;

/**
 * @author: wanhy
 * @date: 2020/1/4 11:53
 */

public class Tools {
    /**
     * @param
     * @Description: 参数验证
     */
    public static boolean vaildeParam(Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (null == args[i]) {
                return false;
            } else {
                if (args[i].getClass().equals(String.class)) {
                    if ("".equals(args[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void test(String a){
        System.err.println(a);
    }
}
