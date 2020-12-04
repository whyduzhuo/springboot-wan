package com.duzhuo.common.core.delorder;

import com.duzhuo.common.core.del.DeleteEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/12/4 10:33
 */
@MappedSuperclass
@Getter
@Setter
public class DelOrderEntity extends DeleteEntity implements Comparable<DelOrderEntity>{
    private static final long serialVersionUID = 7817636515425357255L;

    @NotNull(message = "请输入排序")
    @ApiModelProperty(value = "排序")
    @Min(value = 0,message = "排序不能小于0")
    private Integer order;

    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull DelOrderEntity o) {
        return this.order-o.getOrder();
    }
}
