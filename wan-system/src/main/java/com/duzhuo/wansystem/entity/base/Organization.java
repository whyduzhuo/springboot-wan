package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: wanhy
 * @date: 2020/1/7 14:33
 */
@Getter
@Setter
@Entity
@ApiModel(value = "部门/机构")
@EqualsAndHashCode(callSuper = true,exclude = {"roleList"})
@Table(name = "T_BASE_Organization")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Organization extends BaseEntity{

    private static final long serialVersionUID = -4567376417944617713L;

    @ApiModelProperty(value = "部门名称",dataType = "String",example = "江西财经大学宣传部")
    private String name;

    @ApiModelProperty(value = "上级部门",dataType = "number")
    private Organization parent;

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
