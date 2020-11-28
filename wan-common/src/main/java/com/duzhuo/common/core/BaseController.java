package com.duzhuo.common.core;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:58
 */

public class BaseController {
    public static final String SEARCH_PREFIX = "search_";

    public void searchParamsTrim(Map<String,Object> searchParams){
        searchParams.forEach((k,v)->{
            searchParams.put(k,v.toString().trim());
        });
    }

    /**
     * 给map的key加上指定的前缀
     * @param params
     * @param prefix
     * @return
     */
    public Map<String, Object> mapKeyAddPre(Map<String, Object> params,String prefix){
        if (null == params || params.size() == 0) {
            return params;
        }
        if (StringUtils.isBlank(prefix)) {
            return params;
        }
        Map<String, Object> paramsNew = new HashMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramsNew.put(prefix + entry.getKey(), entry.getValue());
        }
        return paramsNew;
    }
}
