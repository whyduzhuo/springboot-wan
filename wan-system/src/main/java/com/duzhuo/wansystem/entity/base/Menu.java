package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.enums.YesOrNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author: wanhy
 * @date: 2020/1/7 14:38
 */
@Getter
@Setter
@Entity
@ApiModel(value = "菜单")
@Table(name = "T_BASE_MENU")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true,exclude = {"roleList","children"})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Menu extends BaseEntity implements Comparable<Menu>,Serializable {

    private static final long serialVersionUID = -1674442746152794678L;

    public enum Os{
        /**
         *
         */
        PC端,
        /**
         *
         */
        移动端,
    }
    public enum Type{
        /**
         *
         */
        目录,
        /**
         *
         */
        页面,
        /**
         *
         */
        按钮,
    }

    @JsonProperty
    @ApiModelProperty(value = "菜单名称",dataType = "String")
    private String name;

    @JsonProperty
    @ApiModelProperty(value = "菜单路径",example = "/base/admin/list.html",dataType = "String")
    private String path;

    @JsonProperty
    @ApiModelProperty(value = "菜单编号",dataType = "number")
    private Long num;

    @JsonProperty
    @ApiModelProperty(value = "父级菜单",dataType = "number")
    private Menu parent;

    @JsonProperty
    @ApiModelProperty(value = "排序",example = "1",dataType = "number")
    private Integer order;

    @JsonProperty
    @ApiModelProperty(value = "是否可用")
    private YesOrNo isEnable = YesOrNo.是;

    @JsonProperty
    @ApiModelProperty(value = "移动端还是PC端菜单",dataType = "number")
    private Os os = Os.PC端;

    @JsonProperty
    @ApiModelProperty(value = "页面Or按钮",dataType = "number")
    private Type type;

    @JsonProperty
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "拥有此菜单的全部职务")
    private List<Role> roleList = new ArrayList<>();

    @ApiModelProperty(value = "子菜单")
    private List<Menu> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Menu getParent() {
        return parent;
    }

    @OneToMany(mappedBy = "parent")
    public List<Menu> getChildren() {
        return children;
    }

    @ManyToMany(mappedBy = "menuSet")
    public List<Role> getRoleList() {
        return roleList;
    }

    @Override
    public int compareTo(@NotNull Menu o) {
        return this.order-o.getOrder();
    }

    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }
}
