package com.duzhuo.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: wanhy
 * @date: 2020/2/29 11:28
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel{

    /**
     * 导出到Excel的名字
     */
    String name();

    

    /**
     * Excel宽度默认15个字符
     * @return
     */
    int length() default 15;
}