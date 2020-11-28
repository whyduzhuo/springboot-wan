package com.duzhuo.common.utils;

/**
 * @author: 万宏远
 * @date: 2020/10/18 20:29
 */

public class SQLUtils {

    /**
     *
     * @param sql
     * @return
     */
    public static String countOracle(String sql){
        return  "select count(1) from (" + sql + ") t";
    }

    /**
     * 获取分页sql
     * @param sql
     * @param pageNumber 页码，从1开始
     * @param pageSize 单页数量
     * @return
     */
    public static String pageOracle(String sql,Number pageNumber,Number pageSize){
        Long pageNumberL = pageNumber.longValue();
        Long pageSizel = pageSize.longValue();
        long startNum = (pageNumberL-1)*pageSizel+1;
        long endNum = pageNumberL*pageSizel;
        return "select * from (" +
                "select t.*,rownum row_ from (" + sql + ") t" +
                " where rownum<=" + endNum +
                ") where row_>=" + startNum;
    }
}
