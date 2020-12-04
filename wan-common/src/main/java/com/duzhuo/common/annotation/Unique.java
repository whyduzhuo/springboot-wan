package com.duzhuo.common.annotation;

import com.duzhuo.common.core.base.BaseService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/12 17:15
 */
@Repeatable(Uniques.class)
@Constraint(validatedBy = UniqueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Unique {
    Class <? extends BaseService<?,?>> service();
    /**
     * 唯一约束字段
     * @return
     */
    UniqueColumn[] uniqueColumns();

    String message() default "违反唯一约束!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@code @Unique} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Unique[] value();
    }
}
