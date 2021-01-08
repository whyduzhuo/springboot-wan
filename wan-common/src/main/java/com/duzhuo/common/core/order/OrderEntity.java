package com.duzhuo.common.core.order;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 排序Entity
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/9 17:49
 */

@MappedSuperclass
@Getter
@Setter
public  class OrderEntity extends BaseEntity implements Comparable<OrderEntity>{

    private static final long serialVersionUID = -5966022578391042795L;

    public static final String ORDER_PROPERTY_NAME = "order";

    @NotNull(message = "请输入排序")
    @Column(name = "orders")
    @ApiModelProperty(value = "排序")
    @Min(value = 0,message = "排序不能小于0")
    private Integer order;


    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull OrderEntity o) {
        return this.order-o.getOrder();
    }
}
