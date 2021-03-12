package com.duzhuo.wansystem.entity.activiti;

import com.duzhuo.common.annotation.TimeString;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.wansystem.entity.base.Admin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/11 16:06
 */
@Getter
@Setter
@Entity
@ApiModel(value = "用户")
@Table(name = "T_LEAVE")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Leave extends BaseEntity {
    private static final long serialVersionUID = 8312308092081394446L;
    /**
     * 流程定义key
     */
    public static final String PROC_DEF_KEY = "leave_process";

    @ManyToOne
    @NotNull(message = "申请人不可为空")
    @ApiModelProperty(value = "申请人")
    @JoinColumn(name = "ADMIN_ID")
    private Admin admin;

    @NotBlank(message = "标题不可为空")
    @ApiModelProperty(value = "标题")
    private String title;

    @NotBlank(message = "请填写原因")
    @ApiModelProperty(value = "原因")
    private String reason;

    @NotBlank(message = "请输入开始时间")
    @ApiModelProperty(value = "开始时间")
    @TimeString("yyyy-MM-dd HH:00")
    private String startTime;

    @NotBlank(message = "请输入结束时间")
    @ApiModelProperty(value = "结束时间")
    @TimeString("yyyy-MM-dd HH:00")
    private String endTime;

    //@NotBlank(message = "流程实例id不可为空")
    @ApiModelProperty(value = "流程实例id")
    private String instanceId;
}
