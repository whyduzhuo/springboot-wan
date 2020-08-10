package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.*;

/**
 *
 * @author: wanhy
 * @date: 2020/1/7 14:32
 */
@ApiModel(value = "角色/职务",description = "此处的职务相当于原来的角色，部门-<职务><菜单。用户><职务><菜单")
@Entity
@Table(name = "T_BASE_ROLE")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Role extends BaseEntity {
    public enum Type{
        /**
         *普通职务可以修改
         */
        普通职务,
        /**
         * 固定职务不可修改
         * 也不可添加人员
         */
        固定职务
    }

    /**
     * 职务类别
     */
    private Type type;

    @JsonProperty
    @ApiModelProperty(value = "归属部门")
    private Organization organization;

    @JsonProperty
    @ApiModelProperty(value = "职务名称",example = "部长")
    private String name;

    @ApiModelProperty(value = "多对多菜单列表")
    private List<Menu> menuSet = new LinkedList<>();

    @ApiModelProperty(value = "拥有该职务的全部用户")
    private List<Admin> adminSet = new ArrayList<>();

    public Type getType() {
        return type;
    }

    public Role setType(Type type) {
        this.type = type;
        return this;
    }

    @ManyToOne
    @JoinColumn(name = "ORG_ID")
    public Organization getOrganization() {
        return organization;
    }

    public Role setOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ROLE_MENU",
            joinColumns = @JoinColumn(name="ROLE_ID",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id",referencedColumnName = "id"))
    public List<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(List<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    @ManyToMany(mappedBy = "roleSet",fetch = FetchType.LAZY)
    public List<Admin> getAdminSet() {
        return adminSet;
    }

    public void setAdminSet(List<Admin> adminSet) {
        this.adminSet = adminSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Role role = (Role) o;
        return Objects.equals(getOrganization(), role.getOrganization()) &&
                Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOrganization(), getName());
    }
}
