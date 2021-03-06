package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.annotation.Unique;
import com.duzhuo.common.annotation.UniqueColumn;
import com.duzhuo.common.core.order.OrderEntity;
import com.duzhuo.common.enums.YesOrNo;
import com.duzhuo.wansystem.entity.base.po.RolePo;
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
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 14:38
 */
@Getter
@Setter
@Entity
@ApiModel(value = "菜单")
@Table(name = "T_BASE_MENU")
@EqualsAndHashCode(callSuper = true,exclude = {"roleList","children"})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
@Unique(service = MenuService.class,message = "菜单名称重复",uniqueColumns = {@UniqueColumn("parent"),@UniqueColumn("name")})
@Unique(service = MenuService.class,message = "菜单编号重复",uniqueColumns = {@UniqueColumn("num")})
@Unique(service = MenuService.class,message = "菜单排序重复",uniqueColumns = {@UniqueColumn("parent"),@UniqueColumn("order")})
public class Menu extends OrderEntity implements Serializable {
    /**
     * 按钮图标
     */
    public static final String BUTTON_ICON = "/static/zTree_v3-master/css/zTreeStyle/img/diy/10.png";
    /**
     * 菜单图标
     */
    public static final String PAGE_ICON = "/static/zTree_v3-master/css/zTreeStyle/img/diy/2.png";

    /**
     * 菜单图标
     */
    public static final String PAGE_GROUP_ICON = "/static/zTree_v3-master/css/zTreeStyle/img/diy/11.png";


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
        /**
         * 页面组内可以添加页面，默认url = /base/menu/menuGroup?num=#num
         */
        页面组,
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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ApiModelProperty(value = "父级菜单",dataType = "number")
    private Menu parent;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "menuList")
    @ApiModelProperty(value = "拥有此菜单的全部职务")
    private List<RolePo> roleList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    @ApiModelProperty(value = "子菜单")
    private List<Menu> children = new ArrayList<>();




}
