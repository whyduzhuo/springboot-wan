package com.duzhuo.common.utils;

import java.util.List;

/**
 * 字符串工具类
 * 
 * @author 万宏远
 * @date 2020/1/1 15:57
 */
public class StringUtils {

    /**
     * 截取字符串
     * 
     * @param str 字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return "";
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return "";
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     * 
     * @param str 字符串
     * @param start 开始
     * @param end 结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return "";
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return "";
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 是否包含字符串
     * 
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(s.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将集合拼接字符串
     * @param stringList
     * @param spit
     * @return
     */
    public static String listToString(List<String> stringList,String spit){
        if (stringList.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<stringList.size();i++){
            if (i==0){
                sb.append(stringList.get(i));
            }else {
                sb.append(spit).append(stringList.get(i));
            }
        }
        return sb.toString();
    }
}