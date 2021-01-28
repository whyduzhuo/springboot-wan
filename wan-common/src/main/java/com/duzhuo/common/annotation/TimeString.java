package com.duzhuo.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用于校验字符串类型的日期格式
 * value 如 yyyy-MM-dd  yyyy/MM/dd  yyyy-MM-dd HH:mm:ss    yyyy-MM-dd HH:mm:ss SSS
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/23 17:20
 */
@Constraint(validatedBy = TimeStringValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeString {
    String value();

    String message() default "日期格式有误！";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@code @Unique} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        TimeString[] value();
    }
}
