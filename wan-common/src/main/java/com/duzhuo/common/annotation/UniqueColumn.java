package com.duzhuo.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/13 11:02
 */
@Target({})
@Retention(RUNTIME)
public @interface UniqueColumn {
    String value();

    /**
     * 字段值 等于
     * @return
     */
    String includeValue() default "";

    /**
     * 字段值 不等于
     * @return
     */
    String excludeValue() default "";

    /**
     * 是否是父类属性
     * @return
     */
    boolean parentFiled() default false;
}
