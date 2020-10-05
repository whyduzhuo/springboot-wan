package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author: wanhy
 * @date: 2020/1/7 14:32
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true,exclude = {"menuList","adminList"})
@ApiModel(value = "角色/职务",description = "此处的职务相当于原来的角色，部门-<职务><菜单。用户><职务><菜单")
@Table(name = "T_BASE_ROLE")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5816388758817962942L;

    public enum Type {
        /**
         * 普通职务可以修改
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

    @ApiModelProperty(value = "归属部门")
    private Organization organization;

    @JsonProperty
    @ApiModelProperty(value = "职务名称", example = "部长")
    private String name;

    @JsonProperty
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "多对多菜单列表")
    private List<Menu> menuList = new LinkedList<>();

    @ApiModelProperty(value = "拥有该职务的全部用户")
    private List<Admin> adminList = new ArrayList<>();

    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "ORG_ID")
    public Organization getOrganization() {
        return organization;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ROLE_MENU",
            joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    public List<Menu> getMenuList() {
        return menuList.stream().distinct().collect(Collectors.toList());
    }


    @ManyToMany(mappedBy = "roleList")
    public List<Admin> getAdminList() {
        return adminList;
    }
}

