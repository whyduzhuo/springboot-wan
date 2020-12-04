package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.delorder.DelOrderEntity;
import com.duzhuo.wansystem.service.base.OrganizationService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@Unique(service = OrganizationService.class,message = "部门名称已存在",uniqueColumns={@UniqueColumn(value = "name",includeValue = "0")})
public class Organization extends DelOrderEntity implements Serializable {
    private static final long serialVersionUID = -4567376417944617713L;

    public enum Type{
        /**
         *
         */
        机构部门,
        /**
         * 固定节点不可删除修改
         */
        固定节点,
    }

    @NotBlank(message = "请输入部门名称")
    @ApiModelProperty(value = "部门名称",dataType = "String",example = "江西财经大学宣传部")
    private String name;

    @NotNull(message = "请选择上级部门")
    @ApiModelProperty(value = "上级部门",dataType = "number")
    private Organization parent;

    @NotNull
    private Type type = Type.机构部门;

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
