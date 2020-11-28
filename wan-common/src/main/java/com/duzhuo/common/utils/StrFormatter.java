package com.duzhuo.common.utils;

/**
 * 字符串格式化
 * 
 * @author 万宏远
 * @email: 1434495271@qq.com
 * @date 2020/1/1 15:57
 */
public class StrFormatter {

    /**
     *
     * @param i
     * @param len
     * @return
     */
    public static String formatNumber(int i,int len){
        String a = String.valueOf(i);
        for (int i1 = 0;i1<len;i1++){
            if (a.length()==len){
                return a;
            }
            a="0"+a;
        }
        return "";
    }
}
