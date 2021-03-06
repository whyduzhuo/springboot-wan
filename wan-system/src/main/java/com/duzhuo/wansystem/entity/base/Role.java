package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.order.OrderEntity;
import com.duzhuo.wansystem.service.base.RoleService;
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

/**
 *
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 14:32
 */
@Getter
@Setter
@Entity
@ApiModel(value = "角色/职务",description = "此处的职务相当于原来的角色，部门-<职务><菜单。用户><职务><菜单")
@Table(name = "T_BASE_ROLE")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@Unique(service = RoleService.class,message = "角色已存在",uniqueColumns = {@UniqueColumn("name")})
public class Role extends OrderEntity implements Serializable {

    private static final long serialVersionUID = 5816388758817962942L;

    public enum TypeEnum {
        /**
         * 普通职务可以修改
         */
        自定义角色,
        /**
         * 固定职务不可修改
         * 也不可添加人员
         */
        固定角色
    }

    /**
     * 角色类别
     */
    @NotNull(message = "角色类别不可为空！")
    @Column(name = "type")
    private TypeEnum type;

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
        if (type==TypeEnum.固定角色){
            return "<span  class=\"label label-danger\">"+this.type+"</span>";
        }
        if (type==TypeEnum.自定义角色){
            return "<span  class=\"label label-success\">"+this.type+"</span>";
        }
        return "";
    }

}

