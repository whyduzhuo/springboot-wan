package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.ProFileDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 项目文件
 * @author: wanhy
 * @date: 2020/1/2 8:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ProFileService extends BaseService<ProFile,Long>{
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // pdf
            "pdf" };

    @Resource
    private ProFileDao proFileDao;
    @Value("${wan.profile.file-path}")
    private String filePath;
    @Value("${wan.profile.max-size}")
    private String maxSize;
    @Value("${wan.profile.max-file-name}")
    private Integer maxFileName;

    @Resource
    public void setBaseDao(ProFileDao proFileDao){
        super.setBaseDao(proFileDao);
    }

    public void downLoad(@NotNull Long id, HttpServletResponse response) throws IOException{
        ProFile proFile = super.find(id);
        if (proFile==null){
            throw new FileNotFoundException("文件不存在！");
        }
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public Message upload(MultipartFile file) {
        if (file==null){
            throw new ServiceException("文件为空！");
        }
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > maxFileName) {
            throw new ServiceException("文件名过长！最大长度"+maxFileName);
        }
        if (isTooLong(file.getSize())){
            throw new ServiceException("文件过大！最大限制："+maxSize);
        }

        return Message.success("www");
    }

    /**
     * 保存上传记录
     * @param proFileVO
     * @return
     */
    public Message addData(ProFile proFileVO){
        ProFile proFile=super.save(proFileVO);
        return Message.success("保存成功！",proFile);
    }

    /**
     * 判断文件是否超过限制大小
     * @param size 文件大小 单位B
     * @return
     */
    public Boolean isTooLong(Long size){
        Long allowedMaxLongSize = 0L;
        maxSize=maxSize.toUpperCase();

        if (maxSize.contains("KB")){
            allowedMaxLongSize = Long.valueOf(maxSize.replace("KB",""))*1024;
        }
        if (maxSize.contains("MB")){
            allowedMaxLongSize =Long.valueOf(maxSize.replace("MB",""))*1024*1024;
        }
        if (maxSize.contains("GB")){
            allowedMaxLongSize =Long.valueOf(maxSize.replace("GB",""))*1024*1024*1024;
        }
        return size>allowedMaxLongSize;
    }
}
