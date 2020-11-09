package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: wanhy
 * @date: 2020/8/10 10:57
 */
@Entity
@Data
@Accessors(chain = true)
@Table(name = "T_BASE_DICTIONARY")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@ApiModel(value = "字典")
public class Dictionary  extends BaseEntity{

    private static final long serialVersionUID = 1448607782295439386L;

    public enum Status{
        启用,
        禁用
    }

    @NotNull(message = "请选择模块")
    @ApiModelProperty(value = "模块")
    private DictModel dictModel;

    @NotBlank(message = "请输入字典编码")
    @ApiModelProperty(value = "编码")
    private String code;

    @NotBlank(message = "请输入字典值")
    @ApiModelProperty(value = "值")
    private String value;

    @NotNull(message = "请选择启用状态")
    @ApiModelProperty(value = "状态")
    private Status status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "MODEL_ID")
    public DictModel getDictModel() {
        return dictModel;
    }
}
