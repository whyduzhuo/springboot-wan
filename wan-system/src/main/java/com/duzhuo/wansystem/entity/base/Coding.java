package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: wanhy
 * @date: 2020/1/13 15:57
 */
@Entity
@Data
@Accessors(chain = true)
@ApiModel(value = "代码生成")
@Table(name = "T_BASE_CODING")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Coding extends BaseEntity{

    private static final long serialVersionUID = 7684742710608529386L;

    @JsonProperty
    @ApiModelProperty(value ="作者")
    private String author;

    @JsonProperty
    @ApiModelProperty(value = "业务名")
    private String module;

    @JsonProperty
    @ApiModelProperty(value = "完整类名")
    private String entityPackages;

    @JsonProperty
    @ApiModelProperty(value = "类名")
    private String entityName;

    @JsonProperty
    @ApiModelProperty(value = "类名小写")
    private String lowEntityName;

    @JsonProperty
    @ApiModelProperty(value = "系统")
    private String system;

    @JsonProperty
    @ApiModelProperty(value = "serviev包名")
    private String servicepackage;

    @JsonProperty
    @ApiModelProperty(value = "controller包名")
    private String controllerpackage;

    @JsonProperty
    @ApiModelProperty(value = "dao包名")
    private String daopackage;

    @JsonProperty
    @Transient
    private String createDateStr;

    @Transient
    public String getEntityName() {
        return entityName;
    }

    @Transient
    public String getLowEntityName() {
        return lowEntityName;
    }

    @Transient
    public String getSystem() {
        return system;
    }

    @Transient
    public String getCreateDateStr() {
        return createDateStr;
    }

    public void cal(){
        String[] arr = this.entityPackages.split("\\.");
        this.entityName = arr[arr.length-1];
        char first = this.entityName.charAt(0);
        first+=32;
        this.lowEntityName = first+this.entityName.substring(1,this.entityName.length());
        this.createDateStr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.system = arr[arr.length-2];
    }
}
