package com.duzhuo.wansystem.entity.base;

import com.duzhuo.common.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.unit.DataSize;

import javax.persistence.*;

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
public class ProFile extends BaseEntity{

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

    @ApiModelProperty(value = "上传者")
    private Admin admin;

    @Transient
    @ApiModelProperty(value = "文件大小，带单位",notes = "根据大小定单位，带小数，计算属性无需存储",example = "100MB")
    private String fileSizeStr;

    @Transient
    @ApiModelProperty(value = "文件下载路径",notes = "计算属性，无需存储")
    private String downloadPath;

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID")
    public Admin getAdmin() {
        return admin;
    }

    @Transient
    public String getFileSizeStr(){
        return DataSize.ofBytes(this.fileSize).toString();
    }

    @Transient
    public String getDownloadPath() {
        return path+uuid+suffix;
    }


}
