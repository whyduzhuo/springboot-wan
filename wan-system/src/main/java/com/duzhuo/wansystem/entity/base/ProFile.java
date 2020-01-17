package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 项目文件
 * @author: wanhy
 * @date: 2020/1/9 9:07
 */
@Entity
@Table(name = "T_BASE_ProFile")
@ApiModel(value = "项目文件")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class ProFile extends BaseEntity{

}
