package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.core.del.DeleteEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.unit.DataSize;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 项目文件
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/9 9:07
 */
@Data
@Entity
@ApiModel(value = "项目文件")
@EqualsAndHashCode(callSuper = true)
@Table(name = "T_BASE_PROFILE")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class ProFile extends DeleteEntity{
    public enum Status{
        /**
         * 默认文件夹
         */
        DEFAULT,
        /**
         * 开放文件夹
         */
        PUBLIC,
        /**
         * 私有文件夹
         */
        PRIVATE,
    }

    private static final long serialVersionUID = 6994839513547840533L;

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

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    @ApiModelProperty(value = "上传者")
    private Admin admin;

    @ApiModelProperty(value = "MD5值",notes = "用于文件筛选，防止提交重复文件")
    private String md5;

    @ApiModelProperty(value = "类别")
    private Status status;



    @Transient
    @ApiModelProperty(value = "文件大小，带单位",notes = "根据大小定单位，带小数，计算属性无需存储",example = "100MB")
    private String fileSizeStr;

    @Transient
    @ApiModelProperty(value = "文件下载路径",notes = "计算属性，无需存储")
    private String downloadPath;



    @Transient
    public String getFileSizeStr(){
        if (this.fileSize<1024){
            return this.fileSize+"B";
        }
        if (this.fileSize<1024*1024){
            DecimalFormat df1 = new DecimalFormat("0.00");
            return new BigDecimal(this.fileSize).divide(new BigDecimal(1024),2, RoundingMode.HALF_UP).toString() +"KB";
        }
        if (this.fileSize<1024*1024*1024){
            return new BigDecimal(this.fileSize).divide(new BigDecimal(1024*1024),2,RoundingMode.HALF_UP).toString() +"MB";
        }else {
            return new BigDecimal(this.fileSize).divide(new BigDecimal(1024*1024*1024),2,RoundingMode.HALF_UP).toString() +"GB";
        }
    }

    @Transient
    public String getDownloadPath() {
        return path+uuid+suffix;
    }


    @Transient
    @ApiModelProperty(value = "是否存在！")
    private boolean exit;


}
