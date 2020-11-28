package com.duzhuo.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件配置信息
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/19 16:57
 */
@Component
@ConfigurationProperties(prefix = "wan.profile")
public class ProFileConfig {

    /**
     * 文件下载路径
     */
    private  String filePath;
    /**
     * 文件地址对应的项目虚拟路径
     */
    private  String fileVirtualPath;
    /**
     *文件最大限制
     */
    private  String maxSize;
    /**
     *件名称最长限制
     */
    private Integer maxFileName;
    /**
     *支持的文件格式
     */
    private  String[] supportFileType;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileVirtualPath() {
        return fileVirtualPath;
    }

    public void setFileVirtualPath(String fileVirtualPath) {
        this.fileVirtualPath = fileVirtualPath;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getMaxFileName() {
        return maxFileName;
    }

    public void setMaxFileName(Integer maxFileName) {
        this.maxFileName = maxFileName;
    }

    public String[] getSupportFileType() {
        return supportFileType;
    }

    public void setSupportFileType(String... supportFileType) {
        this.supportFileType = supportFileType;
    }
}
