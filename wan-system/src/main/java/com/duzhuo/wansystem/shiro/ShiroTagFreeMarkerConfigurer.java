package com.duzhuo.wansystem.shiro;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;


/**
 * @author: wanhy
 * @date: 2020/8/15 9:29
 */
@Component
class ShiroTagsFreeMarkerCfg  {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        // "设置freeMarker 的shiro 标签"
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }
}