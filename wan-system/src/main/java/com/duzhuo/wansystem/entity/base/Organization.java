package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.DeleteEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 14:33
 */
@Getter
@Setter
@Entity
@ApiModel(value = "部门/机构")
@EqualsAndHashCode(callSuper = true,exclude = {"roleList"})
@Table(name = "T_BASE_Organization")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Organization extends DeleteEntity{

    private static final long serialVersionUID = -4567376417944617713L;

    @ApiModelProperty(value = "部门名称",dataType = "String",example = "江西财经大学宣传部")
    private String name;

    @ApiModelProperty(value = "上级部门",dataType = "number")
    private Organization parent;

    @NotNull(message = "请输入排序")
    @ApiModelProperty(value = "排序")
    @Min(value = 0,message = "排序不能小于0")
    private Integer order;

    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }

    @JsonIgnore
    @ApiModelProperty(value = "部门下的全部职务",notes = "一对多职务表")
    private List<Role> roleList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Organization getParent() {
        return parent;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "organization")
    public List<Role> getRoleList() {
        return roleList;
    }
}
