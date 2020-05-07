package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.unit.DataSize;

import javax.persistence.*;

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

    @ApiModelProperty(value = "文件名称",notes = "uuid",example = "4195756d-aa3b-45ce-998e-63e1a4f4a262")
    private String uuid;

    @ApiModelProperty(value = "文件保存路径",example = "/文件虚拟路径/2018/0109/")
    private String path;

    @ApiModelProperty(value = "文件大小",notes = "单位B")
    private Long fileSize;

    @ApiModelProperty(value = "文件后缀",example = ".doc")
    private String suffix;

    @ApiModelProperty(value = "原文件名,不带后缀",example = "8月5日会议纪要")
    private String original;

    @ApiModelProperty(value = "上传者")
    private Admin admin;





    /**计算属性*/
    @ApiModelProperty(value = "文件大小，带单位",notes = "根据大小定单位，带小数，计算属性无需存储",example = "100MB")
    private String fileSizeStr;

    @ApiModelProperty(value = "文件下载路径",notes = "计算属性，无需存储")
    private String downloadPath;


    @JsonProperty
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
        return DataSize.ofBytes(this.fileSize).toString();
    }


    @JsonProperty
    @Transient
    public String getDownloadPath() {
        return path+uuid+suffix;
    }

}
