package com.duzhuo.wansystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/8/14 10:46
 */
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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "父节点id")
    private Long pid;

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "节点标题")
    private String title;

    @ApiModelProperty(value = "是否选中")
    private boolean checked = false;

    @ApiModelProperty(value = "是否展开")
    private boolean open = false;

    @ApiModelProperty(value = "是否可勾选")
    private boolean nocheck =false;

    @ApiModelProperty(value = "编号")
    private String num;

    private String icon;

    private String type;

    @ApiModelProperty("子节点")
    private List children = new ArrayList<>();

    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @JsonProperty
    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @JsonProperty
    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    @JsonProperty
    public String isNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
