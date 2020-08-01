package com.duzhuo.common.core;

import java.util.Map;
import java.util.Set;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:58
 */

public class BaseController {
    public void searchParamsTrim(Map<String,Object> searchParams){
        Set<String> keys = searchParams.keySet();
        keys.forEach(k->searchParams.get(k).toString().trim());
    }
}
