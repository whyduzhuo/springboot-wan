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
 * @author: wanhy
 * @date: 2020/1/1 16:57
 */
@ApiModel(value = "用户")
@Entity
@Table(name = "T_BASE_ADMIN")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Admin extends BaseEntity implements Cloneable {

    @JsonProperty
    @ApiModelProperty(value = "账号,一卡通号",dataType = "number",example = "1200402570")
    private String username;

    @JsonProperty
    @ApiModelProperty(value = "真实姓名",dataType = "String",example = "张三")
    private String realname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "全部职务")
    private Set<Role> roleSet = new HashSet<>();


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JoinTable(name = "T_BASE_ADMIN_ROLE",joinColumns = @JoinColumn(name="ADMIN_ID",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "ROLE_id",referencedColumnName = "id"))
    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Admin admin = (Admin) o;
        return Objects.equals(getUsername(), admin.getUsername()) &&
                Objects.equals(getRealname(), admin.getRealname()) &&
                Objects.equals(getPassword(), admin.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUsername(), getRealname(), getPassword());
    }
}
