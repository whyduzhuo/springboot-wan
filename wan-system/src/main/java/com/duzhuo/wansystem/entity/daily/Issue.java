package com.duzhuo.wansystem.entity.daily;

import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.wansystem.entity.base.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/5/19 17:49
 */
@ApiModel(value = "任务")
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
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

    @ManyToOne
    @JoinColumn(name = "CREATE_ADMIN_ID")
    @ApiModelProperty(value = "创建人")
    private Admin createAdmin;

    @ApiModelProperty(value = "任务状态")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "receiver_admin_id")
    @ApiModelProperty(value = "接单人")
    private Admin receiver;

    @ApiModelProperty(value = "接单人备注")
    private String receiverRemark;


}
