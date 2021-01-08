package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.enums.YesOrNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 操作日志
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/4 11:01
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_BASE_SYSOPERLOG")
@ApiModel(value = "操作日志")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class SysOperLog extends BaseEntity {

    private static final long serialVersionUID = 3011338163066632943L;

    @ApiModelProperty(value = "操作模块",dataType = "String")
    private String title;

    @ApiModelProperty(value = "业务类型",example = "新增",dataType = "number")
    private OperateType operateType;

    @ApiModelProperty(value = "请求方式",dataType = "String")
    private String method;

    @ApiModelProperty(value = "客户端",dataType = "String")
    private String os;

    @ApiModelProperty(value = "浏览器内核",dataType = "String")
    private String browser;

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    @ApiModelProperty(value = "操作人",dataType = "number")
    private Admin admin;

    @ApiModelProperty(value = "请求方地址",dataType = "String")
    private String operUrl;

    @ApiModelProperty(value = "请求方IP",dataType = "String")
    private String operIp;

    @ApiModelProperty(value = "请求参数",dataType = "String")
    private String operParm;

    @ApiModelProperty(value = "响应结果",dataType = "Json")
    private String jsonResult;

    @ApiModelProperty(value = "状态",notes = "是否成功",dataType = "number")
    private YesOrNo status;

    @ApiModelProperty(value = "是否异常")
    private YesOrNo haveException = YesOrNo.否;

    @ApiModelProperty(value = "错误消息",dataType ="String")
    private String errorMsg;


    @Transient
    public String getStatusHtml(){
        if (this.status==YesOrNo.是){
            return "<span  class=\"label label-success\">"+this.status+"</span>";
        }
        if (this.status==YesOrNo.否){
            return "<span  class=\"label label-danger\">"+this.status+"</span>";
        }
        if(this.status==YesOrNo.未知){
            return "<span  class=\"label label-waring\">"+this.status+"</span>";
        }
        return "";
    }

    @Transient
    public String getHaveExceptionHtml(){
        if (this.haveException==YesOrNo.否){
            return "<span  class=\"label label-success\">"+this.haveException+"</span>";
        }
        if (this.haveException==YesOrNo.是){
            return "<span  class=\"label label-danger\">"+this.haveException+"</span>";
        }
        if(this.haveException==YesOrNo.未知){
            return "<span  class=\"label label-warning\">"+this.haveException+"</span>";
        }
        return "";
    }
}
