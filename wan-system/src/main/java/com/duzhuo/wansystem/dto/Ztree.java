package com.duzhuo.wansystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/14 10:46
 */
@Getter
@Setter
@Api(value = "Ztree树")
public class Ztree implements Serializable,Comparable<Ztree>{
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

    private Integer orders;

    @ApiModelProperty(value = "编号")
    private String num;

    private String icon;


    private String type;

    private String urlPath;

    @JsonProperty
    @ApiModelProperty("子节点")
    private List<Ztree> children = new ArrayList<>();

    @JsonProperty
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @Override
    public int compareTo(@NotNull Ztree o) {
        return this.orders-o.getOrders();
    }

    /**
     * 组装树结构
     * @param ztrees
     * @return
     */
    public static List<Ztree> buildTree(Collection<Ztree> ztrees){
        List<Ztree> all = ztrees.stream().distinct().sorted().collect(Collectors.toList());
        List<Ztree> ztreeList = new ArrayList<>();
        Ztree.assembleTree(null,ztreeList,all);
        return ztreeList;
    }


    /**
     * 递归组装树结构
     * @param ztree
     * @param ztreeList
     * @param all
     */
    public static void assembleTree(Ztree ztree,List<Ztree> ztreeList,List<Ztree> all){
        all.forEach(a->{
            Boolean b = (ztree==null && a.getPid()==null) || (ztree!=null && a.getPid()!=null && ztree.getId().equals(a.getPid()));
            if (b){
                assembleTree(a,a.getChildren(),all);
                ztreeList.add(a);
            }
        });
    }


    /**
     * 带勾选框的树形结构
     * @param allZtrees 全部节点
     * @param checkedZtrees checkedZtrees 已勾选的节点
     * @return
     */
    public static List<Ztree> buildTree(Collection<Ztree> allZtrees,Collection<Ztree> checkedZtrees){
        List<Ztree> all = allZtrees.stream().distinct().sorted().collect(Collectors.toList());
        allZtrees.forEach(a->a.setChecked(Ztree.checked(a,checkedZtrees)));
        List<Ztree> ztreeList = new ArrayList<>();
        Ztree.assembleTree(null,ztreeList,all);
        return ztreeList;
    }

    /**
     * 判断某个节点是否被选中
     * @param ztree
     * @param checkedZtrees
     * @return
     */
    public static boolean checked(Ztree ztree,Collection<Ztree> checkedZtrees){
        AtomicBoolean b = new AtomicBoolean(false);
        checkedZtrees.forEach(c->{
            if (ztree.getId().equals(c.getId())){
                b.set(true);
            }
        });
        return b.get();
    }



}
