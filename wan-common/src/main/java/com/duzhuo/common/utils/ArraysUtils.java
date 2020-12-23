package com.duzhuo.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/22 15:08
 */

public class ArraysUtils {
    public static final String BLANK_STRING ="";

/************************************ 数组/集合转字符串 ****************************************/

    /**
     * List<String> 转字符串
     * @param list
     * @param spit 分隔符
     * @return
     */
    public static String stringListToStr(List<String> list, String spit){
        if (list==null || list.size()==0){
            return BLANK_STRING;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<list.size();i++){
            if (i==0){
                stringBuilder.append(list.get(i));
            }else {
                stringBuilder.append(spit).append(list.get(i));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 数组转字符串
     * @param arr
     * @param spit
     * @return
     */
    public static String arrayToStr(String[] arr,String spit){
        if (arr==null || arr.length==0){
            return BLANK_STRING;
        }
        return stringListToStr(Arrays.asList(arr),spit);
    }

    /**
     *
     * @param list
     * @param spit
     * @return
     */
    public static String numberListToStr(List<Number> list, String spit){
        if (list==null || list.size()==0){
            return BLANK_STRING;
        }
        List<String> stringList = list.stream().map(Object::toString).collect(Collectors.toList());
        return stringListToStr(stringList,spit);
    }

    /**
     *
     * @param arr
     * @param spit
     * @return
     */
    public static String arrayToStr(Number[] arr, String spit){
        if (arr==null || arr.length==0){
            return BLANK_STRING;
        }
        return numberListToStr(Arrays.asList(arr),spit);
    }


/************************************** 字符串转数组/集合 **************************************/

    public List<String> toStringList(String str,String spit){
        if (StringUtils.isBlank(str)){
            return new ArrayList<>();
        }
        String[] split = str.split(spit);
        return Collections.singletonList(spit);
    }

    public List<Long> toLongList(String str,String spit){
        if (StringUtils.isBlank(str)){
            return new ArrayList<>();
        }
        List<String> stringList = toStringList(str, spit);
        return stringList.stream().map(Long::valueOf).collect(Collectors.toList());
    }

    public List<Integer> toIntegerList(String str,String spit){
        if (StringUtils.isBlank(str)){
            return new ArrayList<>();
        }
        List<String> stringList = toStringList(str, spit);
        return stringList.stream().map(Integer::valueOf).collect(Collectors.toList());
    }
}
