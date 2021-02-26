package com.duzhuo.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/2/14 19:57
 */
@Component
@ConfigurationProperties(prefix = "wan-oauth")
public class OauthConfig {
    /**
     * 微信 appid
     */
    private String wechartAppid;

    /**
     * 微信密钥
     */
    private String wechartSecret;

    /**
     * 应用授权作用域。企业自建应用固定填写：snsapi_base
     */
    private String wxSnsapiBase;

    /**
     * 重定向后会带上state参数，企业可以填写a-zA-Z0-9的参数值，长度不可超过128个字节
     */
    private String wxState;


    /**
     * 网页授权连接URL，固定
     */
    private String wxConnectOath2AuthorizeUrl;

    /**
     * 获取token地址
     */
    private String wxGetTokenUrl;

    /**
     * 获取用户access_token和openid
     */
    private String userToken;

    /**
     * 获取用户基本信息
     */
    private String userInfo;

    /**
     * 授权回调地址
     */
    private String requestUrl;

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

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getWxConnectOath2AuthorizeUrl() {
        return wxConnectOath2AuthorizeUrl;
    }

    public void setWxConnectOath2AuthorizeUrl(String wxConnectOath2AuthorizeUrl) {
        this.wxConnectOath2AuthorizeUrl = wxConnectOath2AuthorizeUrl;
    }

    public String getWxGetTokenUrl() {
        return wxGetTokenUrl;
    }

    public void setWxGetTokenUrl(String wxGetTokenUrl) {
        this.wxGetTokenUrl = wxGetTokenUrl;
    }

    public String getWxSnsapiBase() {
        return wxSnsapiBase;
    }

    public void setWxSnsapiBase(String wxSnsapiBase) {
        this.wxSnsapiBase = wxSnsapiBase;
    }

    public String getWxState() {
        return wxState;
    }

    public void setWxState(String wxState) {
        this.wxState = wxState;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
