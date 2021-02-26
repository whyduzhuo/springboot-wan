package com.duzhuo.common.annotation;

import java.lang.annotation.*;

/**
 * 分布式锁
 * @author 万宏远
 * @email: 1434495271@qq.com
 * @date 2020/1/1 16:18
 */
@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SynLock {
    /**
     * key值
     */
    String key();

}
