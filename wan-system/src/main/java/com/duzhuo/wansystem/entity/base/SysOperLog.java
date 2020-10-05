package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.enums.YesOrNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * 操作日志
 * @author: wanhy
 * @date: 2020/1/4 11:01
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_BASE_SYSOPERLOG")
@ApiModel(value = "操作日志")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class SysOperLog extends BaseEntity {

    private static final long serialVersionUID = 3011338163066632943L;
    @JsonProperty
    @ApiModelProperty(value = "操作模块",dataType = "String")
    private String title;

    @JsonProperty
    @ApiModelProperty(value = "业务类型",example = "新增",dataType = "number")
    private OperateType operateType;

    @JsonProperty
    @ApiModelProperty(value = "请求方式",dataType = "String")
    private String method;

    @JsonProperty
    @ApiModelProperty(value = "客户端",dataType = "String")
    private String os;

    @JsonProperty
    @ApiModelProperty(value = "操作人",dataType = "number")
    private Admin admin;

    @JsonProperty
    @ApiModelProperty(value = "请求方地址",dataType = "String")
    private String operUrl;

    @JsonProperty
    @ApiModelProperty(value = "请求方IP",dataType = "String")
    private String operIp;

    @JsonProperty
    @ApiModelProperty(value = "请求参数",dataType = "String")
    private String operParm;

    @JsonProperty
    @ApiModelProperty(value = "响应结果",dataType = "Json")
    private String jsonResult;

    @JsonProperty
    @ApiModelProperty(value = "状态",notes = "是否成功",dataType = "number")
    private YesOrNo status;

    @JsonProperty
    @ApiModelProperty(value = "错误消息",dataType ="String")
    private String errorMsg;

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    public Admin getAdmin() {
        return admin;
    }
}
