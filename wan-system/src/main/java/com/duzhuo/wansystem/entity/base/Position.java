package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author: wanhy
 * @date: 2020/1/7 14:32
 */
@ApiModel(value = "职务实体",description = "此处的职务相当于原来的角色，部门-<职务><菜单。用户><职务><菜单")
@Entity
@Table(name = "T_BASE_Position")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Position extends BaseEntity {

    @JsonProperty
    @ApiModelProperty(value = "归属部门")
    private Organization organization;

    @JsonProperty
    @ApiModelProperty(value = "职务名称",example = "部长")
    private String name;

    @ApiModelProperty(value = "多对多菜单列表")
    private Set<Menu> menuSet = new HashSet<>();

    @ApiModelProperty(value = "拥有该职务的全部用户")
    private Set<Admin> adminSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ORG_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_POSITION_MENU",joinColumns = @JoinColumn(name="POSITION_ID",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "menu_id",referencedColumnName = "id"))
    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    @ManyToMany(mappedBy = "positionSet",fetch = FetchType.LAZY)
    public Set<Admin> getAdminSet() {
        return adminSet;
    }

    public void setAdminSet(Set<Admin> adminSet) {
        this.adminSet = adminSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Position position = (Position) o;
        return Objects.equals(getOrganization(), position.getOrganization()) &&
                Objects.equals(getName(), position.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getOrganization(), getName());
    }
}
