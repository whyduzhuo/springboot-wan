package com.duzhuo.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件配置信息
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/19 16:57
 */
@Component
@ConfigurationProperties(prefix = "wan.redis")
public class RedisKeyTimeOutConfig {

    /**
     * shiro redis缓存失效时间
     */
    private  String shiroTimeOut;

    public String getShiroTimeOut() {
        return shiroTimeOut;
    }

    public void setShiroTimeOut(String shiroTimeOut) {
        this.shiroTimeOut = shiroTimeOut;
    }
}
