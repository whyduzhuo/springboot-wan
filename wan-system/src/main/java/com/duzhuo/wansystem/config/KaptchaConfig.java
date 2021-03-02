package com.duzhuo.wansystem.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 谷歌验证码配置文件
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/1 14:47
 */

@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty(Constants.KAPTCHA_BORDER, "no");
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "red");
        properties.setProperty(Constants.KAPTCHA_BORDER_THICKNESS,"5");
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "120");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "30");
        properties.setProperty(Constants.KAPTCHA_PRODUCER_IMPL,"com.google.code.kaptcha.impl.DefaultKaptcha");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL,"com.google.code.kaptcha.text.impl.DefaultTextCreator");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING,"2345678ACEFGHKMNPUWXYedmnp");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES,"Arial, Courier");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "23");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL,"com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty(Constants.KAPTCHA_NOISE_COLOR,"black");
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL,"com.google.code.kaptcha.impl.ShadowGimpy");
        properties.setProperty(Constants.KAPTCHA_BACKGROUND_IMPL,"com.google.code.kaptcha.impl.DefaultBackground");
        properties.setProperty(Constants.KAPTCHA_BACKGROUND_CLR_TO,"white");
        properties.setProperty(Constants.KAPTCHA_WORDRENDERER_IMPL,"com.google.code.kaptcha.text.impl.DefaultWordRenderer");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}