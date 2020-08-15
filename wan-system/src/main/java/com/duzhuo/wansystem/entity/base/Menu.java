package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.enums.YesOrNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;

/**
 * @author: wanhy
 * @date: 2020/1/7 14:38
 */
@ApiModel(value = "菜单")
@Entity
@Table(name = "T_BASE_MENU")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Menu extends BaseEntity implements Comparable<Menu>{

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


    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public YesOrNo getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(YesOrNo isEnable) {
        this.isEnable = isEnable;
    }

    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToMany(mappedBy = "menuList",fetch = FetchType.LAZY)
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleSet) {
        this.roleList = roleSet;
    }

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(getName(), menu.getName()) &&
                Objects.equals(getPath(), menu.getPath()) &&
                Objects.equals(getParent(), menu.getParent()) &&
                Objects.equals(getOrder(), menu.getOrder()) &&
                getIsEnable() == menu.getIsEnable() &&
                getType() == menu.getType();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getName(), getPath(), getParent(), getOrder(), getIsEnable(), getType());
    }

    @Override
    public int compareTo(@NotNull Menu o) {
        return this.order-o.getOrder();
    }
}
