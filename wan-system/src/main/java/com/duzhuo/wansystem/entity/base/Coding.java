package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

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
@ApiModel(value = "代码生成")
@Entity
@Table(name = "T_BASE_CODING")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class Coding extends BaseEntity{
    //作者
    private String author;
    //业务名
    private String module;
    //完整类名
    private String entityPackages;
    //类名
    private String entityName;
    //类名小写
    private String lowEntityName;
    //系统
    private String system;
    private String createDateStr;
    //serviev包名
    private String servicepackage;
    //controller包名
    private String controllerpackage;
    //dao包名
    private String daopackage;


    public void cal(){
        String[] arr = this.entityPackages.split("\\.");
        this.entityName = arr[arr.length-1];
        char first = this.entityName.charAt(0);
        first+=32;
        this.lowEntityName = first+this.entityName.substring(1,this.entityName.length());
        this.createDateStr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        this.system = arr[arr.length-2];
    }

    @JsonProperty
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Transient
    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    @JsonProperty
    public String getEntityPackages() {
        return entityPackages;
    }

    public void setEntityPackages(String entityPackages) {
        this.entityPackages = entityPackages;
    }

    @Transient
    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Transient
    public String getLowEntityName() {
        return lowEntityName;
    }

    public void setLowEntityName(String lowEntityName) {
        this.lowEntityName = lowEntityName;
    }

    @Transient
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @JsonProperty
    public String getServicepackage() {
        return servicepackage;
    }

    public void setServicepackage(String servicepackage) {
        this.servicepackage = servicepackage;
    }

    @JsonProperty
    public String getControllerpackage() {
        return controllerpackage;
    }

    public void setControllerpackage(String controllerpackage) {
        this.controllerpackage = controllerpackage;
    }

    @JsonProperty
    public String getDaopackage() {
        return daopackage;
    }

    public void setDaopackage(String daopackage) {
        this.daopackage = daopackage;
    }

    @Override
    public String toString() {
        return "Coding{" +
                "author='" + author + '\'' +
                ", module='" + module + '\'' +
                ", entityPackages='" + entityPackages + '\'' +
                ", entityName='" + entityName + '\'' +
                ", lowEntityName='" + lowEntityName + '\'' +
                ", system='" + system + '\'' +
                ", createDateStr='" + createDateStr + '\'' +
                ", servicepackage='" + servicepackage + '\'' +
                ", controllerpackage='" + controllerpackage + '\'' +
                ", daopackage='" + daopackage + '\'' +
                '}';
    }
}
