package com.duzhuo.common.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author: wanhy
 * @date: 2020/1/1 18:11
 */

public class Filter implements Serializable {
    private static final long serialVersionUID =1L;
    public enum Operator {
        //等于
        eq,
        //不等于
        ne,
        //大于
        gt,
        //小于
        lt,
        //大于等于
        ge,
        //小于等于
        le,
        //相似
        like,
        //父级相似,只有右%
        parentlike,

        in,

        isNull,

        isNotNull,

        notIn,

        between,
        //集合字段包含某个值(非null)
        has,
        //集合字段不包含某个值(非null)
        notHas,
        //为空
        isEmpty,
        //不为空
        isNotEmpty
        ;

        public static Operator fromString(String value) {
            return Operator.valueOf(value.toLowerCase());
        }
    }

    private static final boolean DEFAULT_IGNORE_CASE = false;

    private String property;

    private Operator operator;

    private Object value;

    private Boolean ignoreCase = DEFAULT_IGNORE_CASE;

    public Filter() {
    }

    public Filter(String property, Operator operator, Object value) {
        this.property = property;
        this.operator = operator;
        this.value = value;
    }

    public Filter(String property, Operator operator, Object value, boolean ignoreCase) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    public static Filter eq(String property, Object value) {
        return new Filter(property, Operator.eq, value);
    }

    public static Filter eq(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.eq, value, ignoreCase);
    }

    public static Filter ne(String property, Object value) {
        return new Filter(property, Operator.ne, value);
    }

    public static Filter ne(String property, Object value, boolean ignoreCase) {
        return new Filter(property, Operator.ne, value, ignoreCase);
    }

    public static Filter between(String property, Object value1, Object value2) {
        Object[] values = new Object[]{value1, value2};
        return new Filter(property, Operator.between, values);
    }

    public static Filter gt(String property, Object value) {
        return new Filter(property, Operator.gt, value);
    }

    public static Filter lt(String property, Object value) {
        return new Filter(property, Operator.lt, value);
    }

    public static Filter ge(String property, Object value) {
        return new Filter(property, Operator.ge, value);
    }

    public static Filter le(String property, Object value) {
        return new Filter(property, Operator.le, value);
    }

    public static Filter like(String property, Object value) {
        return new Filter(property, Operator.like, value);
    }

    public static Filter parentlike(String property, Object value) {
        return new Filter(property, Operator.parentlike, value);
    }

    public static Filter in(String property, Object value) {
        return new Filter(property, Operator.in, value);
    }

    public static Filter isNull(String property) {
        return new Filter(property, Operator.isNull, null);
    }

    public static Filter isNotNull(String property) {
        return new Filter(property, Operator.isNotNull, null);
    }

    public static Filter notIn(String property, Object value) {
        return new Filter(property, Operator.notIn, value);
    }

    public static Filter has(String property, Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new Filter(property, Operator.has, value);
    }

    public static Filter notHas(String property, Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return new Filter(property, Operator.notHas, value);
    }

    public static Filter isEmpty(String property) {
        return new Filter(property, Operator.isEmpty, null);
    }

    public static Filter isNotEmpty(String property) {
        return new Filter(property, Operator.isNotEmpty, null);
    }

    public Filter ignoreCase() {
        this.ignoreCase = true;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Filter other = (Filter) obj;
        return new EqualsBuilder().append(getProperty(), other.getProperty()).append(getOperator(), other.getOperator()).append(getValue(), other.getValue()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getProperty()).append(getOperator()).append(getValue()).toHashCode();
    }

}
