package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: wanhy
 * @date: 2020/1/7 14:33
 */
@ApiModel(value = "部门/机构")
@Entity
@Table(name = "T_BASE_Organization")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Organization extends BaseEntity{

    @JsonProperty
    @ApiModelProperty(value = "部门名称",dataType = "String",example = "江西财经大学宣传部")
    private String name;

    @JsonProperty
    @ApiModelProperty(value = "上级部门",dataType = "number")
    private Organization parent;

    @ApiModelProperty(value = "部门下的全部职务",notes = "一对多职务表")
    private Set<Position> positionSet = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Organization getParent() {
        return parent;
    }

    public void setParent(Organization parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "organization")
    public Set<Position> getPositionSet() {
        return positionSet;
    }

    public void setPositionSet(Set<Position> positionSet) {
        this.positionSet = positionSet;
    }
}
