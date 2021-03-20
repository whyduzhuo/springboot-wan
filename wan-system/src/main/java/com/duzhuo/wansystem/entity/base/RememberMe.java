package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 记住我功能
 * 设计：点击记住我，插入一条数据，
 * 退出登录清除数据
 *
 * 以后登录就不用输入账号密码了
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/20 11:26
 */

@Data
@Entity
@ApiModel(value = "记住我功能")
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_base_remember_me")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class RememberMe extends BaseEntity {
    private static final long serialVersionUID = -2356081794851399206L;

    @NotNull(message = "用户id")
    @ApiModelProperty(value = "用户")
    @JoinColumn(name = "admin_id")
    @ManyToOne
    private Admin admin;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "token")
    private String token;

}
