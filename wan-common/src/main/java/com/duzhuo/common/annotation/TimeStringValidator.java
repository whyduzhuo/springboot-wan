package com.duzhuo.common.annotation;


import org.apache.commons.lang3.time.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/23 17:26
 */

public class TimeStringValidator implements ConstraintValidator<TimeString, String> {

    private TimeString timeString;

    @Override
    public void initialize(TimeString constraintAnnotation) {
        this.timeString = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value==null){
            return true;
        }
        String format = timeString.value();
        try {
            DateUtils.parseDate(value,format);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}