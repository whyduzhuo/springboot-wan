package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author: wanhy
 * @date: 2020/8/10 10:57
 */
@Entity
@Table(name = "T_BASE_DICTIONARY")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Dictionary  extends BaseEntity{
    public enum Status{
        启用,
        禁用
    }

    private DictModel dictModel;

    private String code;

    private String name;

    private Status status;

    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
