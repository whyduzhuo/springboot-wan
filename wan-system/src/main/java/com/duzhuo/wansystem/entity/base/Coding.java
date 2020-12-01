package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/13 15:57
 */
@Entity
@Data
@ApiModel(value = "代码生成")
@Table(name = "T_BASE_CODING")
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Coding extends BaseEntity{

    private static final long serialVersionUID = 7684742710608529386L;

    @NotBlank(message = "作者不可为空")
    @ApiModelProperty(value ="作者")
    private String author;

    @NotBlank(message = "邮箱不可为空")
    @ApiModelProperty(value ="邮箱")
    private String email;

    @NotBlank(message = "业务名不可为空")
    @ApiModelProperty(value = "业务名")
    private String module;

    @NotBlank(message = "完整类名不可为空")
    @ApiModelProperty(value = "完整类名")
    private String entityPackages;

//------------------ filed not in table -------------
    @ApiModelProperty(value = "类名")
    private String entityName;

    @ApiModelProperty(value = "类名小写")
    private String lowEntityName;

    @ApiModelProperty(value = "系统")
    private String system;

    @ApiModelProperty(value = "serviev包名")
    private String servicepackage;

    @ApiModelProperty(value = "controller包名")
    private String controllerpackage;

    @ApiModelProperty(value = "dao包名")
    private String daopackage;

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
