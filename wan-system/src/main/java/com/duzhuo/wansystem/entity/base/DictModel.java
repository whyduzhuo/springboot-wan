package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.wansystem.service.base.DictModelService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/8/10 10:57
 */
@Getter
@Setter
@Entity
@ApiModel(value = "字典模块")
@Accessors(chain = true)
@Table(name = "T_BASE_DICTMODEL")
@EqualsAndHashCode(callSuper = true,exclude = {"dictionaryList"})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_DICT_MODEL", allocationSize = 1)
@Unique(service = DictModelService.class,message = "模块名称已存在",uniqueColumns = @UniqueColumn("modelName"))
@Unique(service = DictModelService.class,message = "模块编码已存在",uniqueColumns = @UniqueColumn("modelCode"))
@Unique(service = DictModelService.class,message = "排序已存在",uniqueColumns = @UniqueColumn("order"))
public class DictModel extends BaseEntity{

    private static final long serialVersionUID = 3988974700266864376L;

    @NotBlank(message = "请输入模块名称")
    @Length(max = 20,min = 2,message = "模块名称字符在2-20之间")
    @ApiModelProperty(value = "模块名称")
    private String modelName;

    @Pattern(regexp = "[A-Z][0-9]{3}",message = "模块编码不符合规范！规范：A001至Z999")
    @ApiModelProperty(value = "模块编码")
    private String modelCode;

    @NotNull(message = "请输入排序")
    @ApiModelProperty(value = "排序")
    @Column(name = "orders")
    @Min(value = 0,message = "排序不能小于0")
    private Integer order;

    @ApiModelProperty(value = "字典")
    private List<Dictionary> dictionaryList;

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

    @OneToMany(mappedBy = "dictModel")
    @OrderBy("STATUS ASC,ORDERS ASC,CODE ASC")
    public List<Dictionary> getDictionaryList() {
        return dictionaryList;
    }
}
