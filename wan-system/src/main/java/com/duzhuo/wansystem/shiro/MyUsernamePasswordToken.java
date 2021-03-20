package com.duzhuo.wansystem.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/15 14:00
 */

public class MyUsernamePasswordToken extends UsernamePasswordToken {

    public MyUsernamePasswordToken(String username, String password, LoginType loginType) {
        super(username,password);
        this.loginType = loginType;
    }

    public enum LoginType{
        /**
         * 密码登录
         */
        PASSWORD,
        /**
         * 短信验证码登录
         */
        SMS,
        /**
         * oauth2.0单点登录平台登录
         */
        OAUTH2,
        /**
         * 从记住我功能登录
         */
        REMEMBER_ME,
    }

    private LoginType loginType = LoginType.PASSWORD;

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
