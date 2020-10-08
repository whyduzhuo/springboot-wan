package com.duzhuo.common.core;

import com.duzhuo.common.utils.StringUtils;
import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:58
 */

public class BaseController {
    public static final String SEARCH_PREFIX = "search_";

    public void searchParamsTrim(Map<String,Object> searchParams){
        Set<String> keys = searchParams.keySet();
        keys.forEach(k->searchParams.get(k).toString().trim());
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
