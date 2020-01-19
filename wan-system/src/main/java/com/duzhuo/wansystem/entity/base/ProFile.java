package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.config.ProFileConfig;
import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 项目文件
 * @author: wanhy
 * @date: 2020/1/9 9:07
 */
@Entity
@Table(name = "T_BASE_PROFILE")
@ApiModel(value = "项目文件")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class ProFile extends BaseEntity{

    @ApiModelProperty(value = "文件名称",notes = "uuid+文件后缀",example = "4195756d-aa3b-45ce-998e-63e1a4f4a262.doc")
    private String name;

    @ApiModelProperty(value = "文件保存路径",example = "/文件虚拟路径/2018/0109/")
    private String path;

    @ApiModelProperty(value = "文件大小",notes = "单位B")
    private Long fileSize;

    @ApiModelProperty(value = "文件大小，带单位",notes = "根据大小定单位，带小数，计算属性无需存储",example = "100MB")
    private String fileSizeStr;

    @ApiModelProperty(value = "宽度",notes = "单位px,只有图片格式才有")
    private Integer width;

    @ApiModelProperty(value = "高度",notes = "单位px,只有图片格式才有")
    private Integer height;

    @ApiModelProperty(value = "文件扩展名",example = ".doc",notes = "计算属性，无需存储")
    private String ext;

    @ApiModelProperty(value = "原文件名",example = "8月5日会议纪要.doc")
    private String original;

    @ApiModelProperty(value = "上传者")
    private Admin admin;

    @ApiModelProperty(value = "文件下载路径",notes = "计算属性，无需存储")
    private String downloadPath;

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty
    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @JsonProperty
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty
    @Transient
    public String getExt() {
        return this.name.substring(name.lastIndexOf("."));
    }

    @JsonProperty
    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @JsonProperty
    @Transient
    public String getFileSizeStr() {
        if (this.fileSize<1024){
            return this.fileSize+"B";
        }
        if (this.fileSize<1024*1024){
            return new BigDecimal(this.fileSize).divide(new BigDecimal(1024),2, RoundingMode.HALF_UP).toString()+"KB";
        }
        if (this.fileSize<1024*1024*1024){
            return new BigDecimal(this.fileSize).divide(new BigDecimal(1024*1024),2, RoundingMode.HALF_UP).toString()+"MB";
        }
        else {
            return new BigDecimal(this.fileSize).divide(new BigDecimal(1024*1024*1024),2, RoundingMode.HALF_UP).toString()+"GB";
        }
    }

    @JsonProperty
    @Transient
    public String getDownloadPath() {
        return path+name;
    }

}
