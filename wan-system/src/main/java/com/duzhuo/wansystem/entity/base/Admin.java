package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.*;

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
    private List<Role> roleList = new ArrayList<>();


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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ADMIN_ROLE",
            joinColumns = @JoinColumn(name="ADMIN_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")
    )
    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleSet) {
        this.roleList = roleSet;
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

    @Transient
    public String getRoleListStr(){
        if (this.roleList.isEmpty()){
            return "无";
        }
        List<String> stringList = new ArrayList<>();
        roleList.forEach(r->{
            stringList.add(r.getName());
        });
        return StringUtils.listToString(stringList,",");
    }
}
