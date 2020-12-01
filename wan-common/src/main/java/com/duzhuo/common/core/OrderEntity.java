package com.duzhuo.common.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 排序Entity
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/11/9 17:49
 */

@MappedSuperclass
@Getter
@Setter
public  class OrderEntity extends BaseEntity{

    private static final long serialVersionUID = -5966022578391042795L;

    public static final String ORDER_PROPERTY_NAME = "order";

    @NotNull(message = "请输入排序")
    @ApiModelProperty(value = "排序")
    @Min(value = 0,message = "排序不能小于0")
    private Integer order;

    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }

}
