package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * @author: wanhy
 * @date: 2020/8/10 10:57
 */
@Entity
@Table(name = "T_BASE_DICTIONARY")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@ApiModel(value = "字典")
public class Dictionary  extends BaseEntity{
    public enum Status{
        启用,
        禁用
    }

    @ApiModelProperty(value = "模块")
    private DictModel dictModel;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "状态")
    private Status status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "MODEL_ID")
    public DictModel getDictModel() {
        return dictModel;
    }

    public void setDictModel(DictModel dictModel) {
        this.dictModel = dictModel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
