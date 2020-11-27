package com.duzhuo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/27 18:04
 */
@Component
@ConfigurationProperties(prefix = "wan.email")
@Getter
@Setter
public class EmailConfig {

    /**
     * 发件方
     */
    private String from;

    /**
     * 发件方用户名
     */
    private String username;

    /**
     * 授权码
     */
    private String password;

    /**
     * 端口
     */
    private String host;


}
