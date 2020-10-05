package com.duzhuo.common.utils;

import com.duzhuo.common.core.CustomSearch;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: wanhy
 * @date: 2020/1/5 14:30
 */

public class CommonUtil {
    /**
     * 初始化页数
     */
    public static void initPage(HttpServletRequest request, CustomSearch customSearch) {
        String pageInit = request.getParameter("pageInit");
        String isSearch = request.getParameter("isSearch");
        if (StringUtils.isNotBlank(pageInit)) {
            if("yes".equals(pageInit) || "on".equals(pageInit) || "true".equals(pageInit)){
                customSearch.setPageNumber(1);
            }

        }
        if (StringUtils.isNotBlank(isSearch)) {
            if("yes".equals(isSearch) || "true".equals(isSearch)){
                customSearch.setPageNumber(1);
            }

        }
    }
}
