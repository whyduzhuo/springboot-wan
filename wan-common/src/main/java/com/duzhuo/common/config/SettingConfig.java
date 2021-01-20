package com.duzhuo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/9 11:15
 */

@Component
@ConfigurationProperties(prefix = "wan.setting")
@Getter
@Setter
public class SettingConfig {
    /**
     * 是否开启错误信息邮件提醒
     */
    private Boolean errMsgEmailReminder;
    /**
     * 错误消息接收邮箱
     */
    private String email;
    /**
     * cookie 记住认证信息的密钥
     */
    private String cipherKey;

}
