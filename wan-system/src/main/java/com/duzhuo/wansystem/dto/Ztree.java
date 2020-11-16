package com.duzhuo.wansystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/8/14 10:46
 */
@Getter
@Setter
@Api(value = "Ztree树")
public class Ztree implements Serializable{
    /**
     * 按钮图标
     */
    public static final String BUTTON_ICON = "/static/zTree_v3-master/css/zTreeStyle/img/diy/10.png";
    /**
     * 菜单图标
     */
    public static final String PAGE_ICON = "/static/zTree_v3-master/css/zTreeStyle/img/diy/2.png";
    /**
     *
     */
    public static final String DICT_ICON = "";

    @JsonProperty
    @ApiModelProperty(value = "id")
    private Long id;

    @JsonProperty
    @ApiModelProperty(value = "父节点id")
    private Long pid;

    @JsonProperty
    @ApiModelProperty(value = "节点名称")
    private String name;

    @JsonProperty
    @ApiModelProperty(value = "节点标题")
    private String title;

    @JsonProperty
    @ApiModelProperty(value = "是否选中")
    private boolean checked = false;

    @JsonProperty
    @ApiModelProperty(value = "是否展开")
    private boolean open = false;

    @JsonProperty
    @ApiModelProperty(value = "是否可勾选")
    private boolean nocheck =false;

    @JsonProperty
    @ApiModelProperty(value = "编号")
    private String num;

    @JsonProperty
    private String icon;

    @JsonProperty
    private String type;

    @JsonProperty
    private String urlPath;

    @JsonProperty
    @ApiModelProperty("子节点")
    private List<Ztree> children = new ArrayList<>();

    @JsonProperty
    @ApiModelProperty(value = "角色id")
    private Long roleId;
}
