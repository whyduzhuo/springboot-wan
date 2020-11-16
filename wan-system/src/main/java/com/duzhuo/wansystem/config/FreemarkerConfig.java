package com.duzhuo.wansystem.config;

import com.duzhuo.common.utils.ServletUtils;
import com.duzhuo.wansystem.freemarker.template.MenuDirective;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author: 万宏远
 * @email:1434495271@qq.com
 * @date: 2020/11/14 10:43
 */

@Component
public class FreemarkerConfig {

    @Resource
    private Configuration configuration;
    @Resource
    private MenuDirective menuDirective;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        configuration.setSharedVariable("menuDirective", menuDirective);
    }

}
