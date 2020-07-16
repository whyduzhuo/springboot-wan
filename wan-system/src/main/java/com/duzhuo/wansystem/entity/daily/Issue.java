package com.duzhuo.wansystem.entity.daily;

import com.duzhuo.common.core.BaseEntity;
import com.duzhuo.wansystem.entity.base.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author: wanhy
 * @date: 2020/5/19 17:49
 */
@ApiModel(value = "任务")
@Entity
@Table(name = "T_DAILY_ISSUE")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Issue extends BaseEntity{
    private enum Status{
        /**
         *
         */
        尚未接单,
        /**
         *
         */
        玩命赶制中,
        /**
         *
         */
        已完成,
        /**
         *
         */
        无法完成,
        /**
         *
         */
        部分完成,
    }

    @NotBlank
    @ApiModelProperty(value = "任务简介",dataType = "String",example = "科研课题导出bug")
    private String title;

    @ApiModelProperty(value = "任务详情",dataType = "String")
    private String content;

    @ApiModelProperty(value = "创建人")
    private Admin createAdmin;

    @ApiModelProperty(value = "任务状态")
    private Status status;

    @ApiModelProperty(value = "接单人")
    private Admin receiver;

    @ApiModelProperty(value = "接单人备注")
    private String receiverRemark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne
    @JoinColumn(name = "CREATE_ADMIN_ID")
    public Admin getCreateAdmin() {
        return createAdmin;
    }

    public void setCreateAdmin(Admin createAdmin) {
        this.createAdmin = createAdmin;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_admin_id")
    public Admin getReceiver() {
        return receiver;
    }

    public void setReceiver(Admin receiver) {
        this.receiver = receiver;
    }

    public String getReceiverRemark() {
        return receiverRemark;
    }

    public void setReceiverRemark(String receiverRemark) {
        this.receiverRemark = receiverRemark;
    }
}
