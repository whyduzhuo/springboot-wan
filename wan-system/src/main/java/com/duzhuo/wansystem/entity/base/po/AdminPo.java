package com.duzhuo.wansystem.entity.base.po;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.core.del.DeleteEntity;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.AdminService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/1 16:57
 */
@Getter
@Setter
@Entity
@ApiModel(value = "用户")
@Table(name = "T_BASE_ADMIN")
@EqualsAndHashCode(callSuper = true,exclude = "roleList")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@Unique(service = AdminService.class,message = "用户名重复",uniqueColumns = @UniqueColumn("username"))
public class AdminPo extends DeleteEntity implements Cloneable,Serializable {

    private static final long serialVersionUID = -6079046386811746580L;

    @NotBlank(message = "用户名不可为空")
    @Length(max = 15,min = 3,message = "账号长度3-15个字符")
    @ApiModelProperty(value = "账号,一卡通号",dataType = "number",example = "1200402570")
    private String username;

    @NotBlank(message = "用户名不可为空")
    @ApiModelProperty(value = "真实姓名",dataType = "String",example = "张三")
    private String realname;

    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ADMIN_ROLE",
            joinColumns = @JoinColumn(name="ADMIN_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")
    )
    @ApiModelProperty(value = "全部角色")
    private List<Role> roleList = new ArrayList<>();

    @Transient
    private String roleListStr;


    @Transient
    public String getRoleListStr(){
        if (this.getRoleList().isEmpty()){
            return "无";
        }
        List<String> stringList = new ArrayList<>();
        roleList.forEach(r-> stringList.add(r.getName()));
        return StringUtils.listToString(stringList,",");
    }

    @Transient
    public String getIsDeleteHtml(){
        if (super.getDelTime()!=0L){
            return "<span  class=\"label label-danger\">已禁用</span>";
        }
        if (super.getDelTime()==0L){
            return "<span  class=\"label label-success\">正常</span>";
        }
        return "";
    }


}
