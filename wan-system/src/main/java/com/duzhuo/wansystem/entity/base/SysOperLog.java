package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.enums.YesOrNo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * 操作日志
 * @author: wanhy
 * @date: 2020/1/4 11:01
 */
@Entity
@Table(name = "T_BASE_SYSOPERLOG")
@ApiModel(value = "操作日志")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class SysOperLog extends BaseEntity {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(OperateType operateType) {
        this.operateType = operateType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ADMIN_ID")
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getOperUrl() {
        return operUrl;
    }

    public void setOperUrl(String operUrl) {
        this.operUrl = operUrl;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperParm() {
        return operParm;
    }

    public void setOperParm(String operParm) {
        this.operParm = operParm;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public YesOrNo getStatus() {
        return status;
    }

    public void setStatus(YesOrNo status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "SysOperLog{" +
                "title='" + title + '\'' +
                ", operateType=" + operateType +
                ", method='" + method + '\'' +
                ", os='" + os + '\'' +
                ", admin=" + admin +
                ", operUrl='" + operUrl + '\'' +
                ", operIp='" + operIp + '\'' +
                ", operParm='" + operParm + '\'' +
                ", jsonResult='" + jsonResult + '\'' +
                ", status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
