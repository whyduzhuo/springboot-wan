package com.duzhuo.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.sql.SQLException;

/**
 * @author: wanhy
 * @date: 2020/2/14 19:57
 */

@ConfigurationProperties(prefix = "wan-oauth")
public class OauthConfig {
    private String wechartAppid;

    private String wechartSecret;

    public String getWechartAppid() {
        return wechartAppid;
    }

    public void setWechartAppid(String wechartAppid) {
        this.wechartAppid = wechartAppid;
    }

    public String getWechartSecret() {
        return wechartSecret;
    }

    public void setWechartSecret(String wechartSecret) {
        this.wechartSecret = wechartSecret;
    }
}
