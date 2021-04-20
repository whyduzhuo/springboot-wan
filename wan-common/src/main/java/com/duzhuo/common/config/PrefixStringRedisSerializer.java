package com.duzhuo.common.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/4/20 11:47
 */
@Component
public class PrefixStringRedisSerializer implements RedisSerializer<String> {
    @Resource
    private SettingConfig settingConfig;


    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, StandardCharsets.UTF_8).replaceFirst(settingConfig.getName()+":", ""));
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        return (string == null ? null : (settingConfig.getName()+":" + string).getBytes(StandardCharsets.UTF_8));
    }
}
