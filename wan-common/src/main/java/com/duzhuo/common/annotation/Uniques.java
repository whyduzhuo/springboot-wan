package com.duzhuo.common.annotation;

import java.lang.annotation.*;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/13 17:15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Uniques {
    Unique[] value();
}
