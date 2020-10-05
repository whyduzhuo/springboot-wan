package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wanhy
 * @date: 2020/1/1 16:57
 */
@Getter
@Setter
@Entity
@Accessors(chain = true)
@ApiModel(value = "用户")
@Table(name = "T_BASE_ADMIN")
@EqualsAndHashCode(callSuper = true,exclude = "roleList")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Admin extends BaseEntity implements Cloneable,Serializable {

    private static final long serialVersionUID = -6079046386811746580L;

    @JsonProperty
    @ApiModelProperty(value = "账号,一卡通号",dataType = "number",example = "1200402570")
    private String username;

    @JsonProperty
    @ApiModelProperty(value = "真实姓名",dataType = "String",example = "张三")
    private String realname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "全部职务")
    private List<Role> roleList;

    @Transient
    private String roleListStr;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ADMIN_ROLE",
            joinColumns = @JoinColumn(name="ADMIN_ID",referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")
    )
    public List<Role> getRoleList() {
        return roleList.stream().distinct().collect(Collectors.toList());
    }

    @Transient
    public String getRoleListStr(){
        if (this.getRoleList().isEmpty()){
            return "无";
        }
        List<String> stringList = new ArrayList<>();
        roleList.forEach(r-> stringList.add(r.getName()));
        return StringUtils.listToString(stringList,",");
    }
}
