package com.duzhuo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 通用配置
 * 
 * @author wna
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Resource
    private ProFileConfig proFileConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
        registry.addResourceHandler(proFileConfig.getFileVirtualPath()+"/**").addResourceLocations("file:" + proFileConfig.getFilePath() + "/");

        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}