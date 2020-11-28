package com.duzhuo.common.utils;

import com.duzhuo.common.core.CustomSearch;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/5 14:30
 */

public class CommonUtil {
    /**
     * 初始化页数
     */
    public static void initPage(HttpServletRequest request, CustomSearch customSearch) {
        String pageInit = request.getParameter("pageInit");
        if (StringUtils.isNotBlank(pageInit)) {
            if("yes".equals(pageInit)){
                customSearch.setPageNumber(1);
            }
        }
    }
}
