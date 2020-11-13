package com.duzhuo.common.annotation;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.utils.SpringUtils;
import com.duzhuo.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 万宏远
 * @date: 2020/11/12 17:26
 */

public class UniqueValidator implements ConstraintValidator<Unique, BaseEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueValidator.class);

    private UniqueColumn[] uniqueColumns;
    private Class<? extends BaseService<?,?>> serviceClass;

    @Override
    public void initialize(Unique annotation) {
        uniqueColumns = annotation.uniqueColumns();
        serviceClass = annotation.service();
    }

    @Override
    public boolean isValid(BaseEntity baseEntity, ConstraintValidatorContext constraintValidatorContext) {
        String beanName = serviceClass.getSimpleName().substring(0,1).toLowerCase()+serviceClass.getSimpleName().substring(1);
        BaseService entityService = SpringUtils.getBean(serviceClass);
        List<Filter> filterList = new ArrayList<>();
        if (baseEntity.getId()!=null){
            filterList.add(Filter.ne(BaseEntity.ID_PROPERTY_NAME,baseEntity.getId()));
        }
        for (UniqueColumn uniqueColumn:uniqueColumns){
            Class clazz = baseEntity.getClass();
            try {
                Field field = clazz.getDeclaredField(uniqueColumn.value());
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();
                Object value = getMethod.invoke(baseEntity);
                filterList.add(Filter.eq(uniqueColumn.value(),value));
                Class<?> type = field.getType();
                if (StringUtils.isNotBlank(uniqueColumn.includeValue())){
                    Object include = getOldTypeValue(uniqueColumn.includeValue(),type);
                    filterList.add(Filter.eq(uniqueColumn.value(),include));
                }
                if (StringUtils.isNotBlank(uniqueColumn.excludeValue())){
                    Object exclude = getOldTypeValue(uniqueColumn.excludeValue(),type);
                    filterList.add(Filter.ne(uniqueColumn.value(),exclude));
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
                throw new RuntimeException("Unique 校验错误");
            }
        }
        long count = entityService.count(filterList);
        return count==0;
    }


    public Object getOldTypeValue(String value,Class<?> type){
        if (type.isEnum()) {
            try {
                Method v = type.getMethod("valueOf", String.class);
                return v.invoke(type, value);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return value;
    }
}