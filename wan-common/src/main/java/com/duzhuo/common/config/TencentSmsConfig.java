package com.duzhuo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯云短信配置
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/11 9:00
 */
@Component
@ConfigurationProperties(prefix = "wan.sms.tencent")
@Getter
@Setter
public class TencentSmsConfig {

    /**
     * APPID
     * APPID 查询https://console.cloud.tencent.com/smsv2/app-manage
     */
    private String appId;

    /**
     * 签名内容
     * https://console.cloud.tencent.com/smsv2/csms-sign
     */
    private String sign;

    /**
     * 模板id
     * https://console.cloud.tencent.com/smsv2/csms-template
     */
    private String templateId;

    /**
     * 密钥id
     * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
     */
    private String secretId;

    /**
     * 密钥key
     * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
     */
    private String secretKey;
}
