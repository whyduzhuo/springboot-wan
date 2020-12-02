package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.OrderEntity;
import com.duzhuo.wansystem.service.base.DictionaryService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/10 10:57
 */
@Entity
@Data
@Table(name = "T_BASE_DICTIONARY")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@ApiModel(value = "字典")
@Unique(service = DictionaryService.class,message = "字典编码已存在!",uniqueColumns = {@UniqueColumn("dictModel"),@UniqueColumn("code")})
@Unique(service = DictionaryService.class,message = "字典值已存在！",uniqueColumns = {@UniqueColumn("dictModel"),@UniqueColumn("value")})
@Unique(service = DictionaryService.class,message = "排序重复",uniqueColumns = {@UniqueColumn("dictModel"),@UniqueColumn(value = "order",parentFiled = true)})
public class Dictionary  extends OrderEntity{

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
    @ApiModelProperty(value = "字典值")
    private String value;

    @NotNull(message = "请选择启用状态")
    @ApiModelProperty(value = "状态")
    private Status status = Status.启用;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "MODEL_ID")
    public DictModel getDictModel() {
        return dictModel;
    }

    @Transient
    public String getStatusHtml(){
        if (status==Status.启用){
            return "<span  class=\"label label-success\">"+this.status+"</span>";
        }
        if (status==Status.禁用){
            return "<span  class=\"label label-danger\">"+this.status+"</span>";
        }
        return "";
    }
}
