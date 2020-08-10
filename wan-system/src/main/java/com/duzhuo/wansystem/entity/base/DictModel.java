package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/8/10 10:57
 */
@ApiModel(value = "字典模块")
public class DictModel extends BaseEntity{

    @ApiModelProperty(value = "模块名称")
    private String modelName;

    @ApiModelProperty(value = "模块编码")
    private String modelCode;

    @ApiModelProperty(value = "字典")
    private List<Dictionary> dictionaryList;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    @OneToMany(mappedBy = "dictModel")
    @OrderBy("CODE ASC")
    public List<Dictionary> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(List<Dictionary> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }

    @Transient
    public List<Dictionary> getDictionaryList(Dictionary.Status status){
        List<Dictionary> dictionaryList = this.getDictionaryList();
        List<Dictionary> dictionaryList1 = new ArrayList<>();
        dictionaryList.forEach(d->{
            if (d.getStatus()==status){
                dictionaryList1.add(d);
            }
        });
        return dictionaryList1;
    }
}