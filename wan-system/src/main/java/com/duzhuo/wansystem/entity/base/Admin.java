package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.service.base.AdminService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:57
 */
@Getter
@Setter
@Entity
@ApiModel(value = "用户")
@Table(name = "T_BASE_ADMIN")
@EqualsAndHashCode(callSuper = true,exclude = "roleSet")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
//@Unique(service = AdminService.class,message = "用户名重复",uniqueColumns = @UniqueColumn("username"))
public class Admin extends BaseEntity implements Cloneable,Serializable {

    private static final long serialVersionUID = -6079046386811746580L;

    @NotBlank(message = "用户名不可为空")
    @Length(max = 15,min = 3)
    @ApiModelProperty(value = "账号,一卡通号",dataType = "number",example = "1200402570")
    private String username;

    @NotBlank(message = "用户名不可为空")
    @ApiModelProperty(value = "真实姓名",dataType = "String",example = "张三")
    private String realname;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull
    @ApiModelProperty(value = "是否禁用")
    private IsDelete isDelete = IsDelete.否;

    @JsonIgnore
    @ApiModelProperty(value = "全部职务")
    private Set<Role> roleSet = new HashSet<>();

    @Transient
    private String roleListStr;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ADMIN_ROLE",
            joinColumns = @JoinColumn(name="ADMIN_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")
    )
    public Set<Role> getRoleSet() {
        return roleSet;
    }

    @Transient
    public String getRoleListStr(){
        if (this.getRoleSet().isEmpty()){
            return "无";
        }
        List<String> stringList = new ArrayList<>();
        roleSet.forEach(r-> stringList.add(r.getName()));
        return StringUtils.listToString(stringList,",");
    }

    @Transient
    public String getIsDeleteHtml(){
        if (this.isDelete==IsDelete.是){
            return "<span  class=\"label label-danger\">已禁用</span>";
        }
        if (this.isDelete==IsDelete.否){
            return "<span  class=\"label label-success\">正常</span>";
        }
        return "";
    }
}
