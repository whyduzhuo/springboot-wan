package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.enums.YesOrNo;
import com.duzhuo.wansystem.service.base.MenuService;
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
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/7 14:38
 */
@Getter
@Setter
@Entity
@ApiModel(value = "菜单")
@Table(name = "T_BASE_MENU")
@EqualsAndHashCode(callSuper = true,exclude = {"roleList","children"})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
//@Unique(service = MenuService.class,message = "菜单名称重复",uniqueColumns = {@UniqueColumn("parent"),@UniqueColumn("name")})
//@Unique(service = MenuService.class,message = "菜单编号重复",uniqueColumns = {@UniqueColumn("num")})
//@Unique(service = MenuService.class,message = "菜单排序重复",uniqueColumns = {@UniqueColumn("parent"),@UniqueColumn("order")})
public class Menu extends BaseEntity implements Comparable<Menu>,Serializable {

    private static final long serialVersionUID = -1674442746152794678L;

    public enum OsEnum{
        /**
         *
         */
        PC端,
        /**
         *
         */
        移动端,
    }
    public enum TypeEnum{
        /**
         *
         */
        目录,
        /**
         *
         */
        页面,
        /**
         *
         */
        按钮,
    }

    @NotBlank(message = "菜单名不能为空")
    @Length(max = 14,min = 2)
    @ApiModelProperty(value = "菜单名称",dataType = "String")
    private String name;

    @NotBlank(message = "请输入url")
    @Length(min = 1,max = 40)
    @ApiModelProperty(value = "菜单路径",example = "/base/admin/list.html",dataType = "String")
    private String path;

    @ApiModelProperty(value = "菜单编号",dataType = "number")
    private Long num;

    @ApiModelProperty(value = "父级菜单",dataType = "number")
    private Menu parent;

    @ApiModelProperty(value = "排序",example = "1",dataType = "number")
    private Integer order;

    @NotNull(message = "启用or禁用")
    @ApiModelProperty(value = "是否可用")
    private YesOrNo isEnable = YesOrNo.是;

    @NotNull(message = "OS不能为空！")
    @ApiModelProperty(value = "移动端还是PC端菜单",dataType = "number")
    private OsEnum os = OsEnum.PC端;

    @NotNull(message="菜单类型不能为空")
    @ApiModelProperty(value = "页面Or按钮",dataType = "number")
    private TypeEnum type;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "拥有此菜单的全部职务")
    private List<Role> roleList = new ArrayList<>();

    @JsonIgnore
    @ApiModelProperty(value = "子菜单")
    private List<Menu> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public List<Menu> getChildren() {
        return children;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "menuSet")
    public List<Role> getRoleList() {
        return roleList;
    }

    @Override
    public int compareTo(Menu o) {
        return this.order-o.getOrder();
    }

    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }
}
