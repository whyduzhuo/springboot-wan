package com.duzhuo.wansystem.entity.base.po;

import com.duzhuo.common.core.order.OrderEntity;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Organization;
import com.duzhuo.wansystem.entity.base.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/20 9:21
 */
@Getter
@Setter
@Entity
@ApiModel(value = "角色/职务",description = "此处的职务相当于原来的角色，部门-<职务><菜单。用户><职务><菜单")
@Table(name = "T_BASE_ROLE")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class RolePo extends OrderEntity implements Serializable {
    private static final long serialVersionUID = 1464571065965216854L;

    /**
     * 角色类别
     */
    @NotNull(message = "角色类别不可为空！")
    @Column(name = "type")
    private Role.TypeEnum type;

    @NotNull(message = "请选择归属部门！")
    @ApiModelProperty(value = "归属部门")
    @ManyToOne
    @JoinColumn(name = "ORG_ID")
    private Organization organization;

    @NotBlank(message = "角色名称不可为空！")
    @Length(max = 20,min = 2,message = "角色名称长度2-20个字符")
    @ApiModelProperty(value = "角色名称", example = "部长")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @Transient
    public Boolean checked;


    @JsonIgnore
    @Transient
    public String getTypeHtml(){
        if (type== Role.TypeEnum.固定角色){
            return "<span  class=\"label label-danger\">"+this.type+"</span>";
        }
        if (type== Role.TypeEnum.自定义角色){
            return "<span  class=\"label label-success\">"+this.type+"</span>";
        }
        return "";
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "T_BASE_ROLE_MENU",
            joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    @ApiModelProperty(value = "多对多菜单列表")
    @OrderBy("order asc")
    private List<Menu> menuList = new ArrayList<>();
}
