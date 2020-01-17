package com.duzhuo.common.annotation;

import com.duzhuo.common.enums.OperateType;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 记录接口的调用记录和传入参数、返回值、异常
 * @author wanhy
 * @date 2020/1/1 16:18
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title();

    /**
     * 功能
     */
    OperateType operateType();

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;


}
