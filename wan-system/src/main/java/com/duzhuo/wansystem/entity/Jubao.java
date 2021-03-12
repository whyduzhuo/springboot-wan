package com.duzhuo.wansystem.entity;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/12 15:47
 */
@Getter
@Setter
@Entity
@ApiModel(value = "城市大脑举报人信息")
@Table(name = "T_JUBAO_2")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Jubao extends BaseEntity{
    private String happenTimeStr;
    private String code;
    private String contactUser;
    private String content;
    private String creatorName;
    private String tel;
}
