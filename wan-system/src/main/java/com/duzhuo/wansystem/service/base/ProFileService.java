package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.config.ProFileConfig;
import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.dao.base.ProFileDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * 项目文件
 * @author: wanhy
 * @date: 2020/1/2 8:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ProFileService extends BaseService<ProFile,Long>{

    @Resource
    private ProFileConfig proFileConfig;

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
    public Message upload(MultipartFile file) throws IOException {
        if (file==null){
            throw new ServiceException("文件为空！");
        }
        String fileName = file.getOriginalFilename();
        Integer f = proFileConfig.getMaxFileName();

        if (fileName.length() > proFileConfig.getMaxFileName()) {
            throw new ServiceException("文件名过长！最大长度"+ proFileConfig.getMaxFileName());
        }
        if (this.isTooLong(file.getSize(),proFileConfig.getMaxSize())){
            throw new ServiceException("文件过大！最大限制："+proFileConfig.getMaxSize());
        }
        if(!this.isSupport(fileName)){
            throw new ServiceException("不支持的文件格式");
        }
        // 获取UUID
        String uuid = UUID.randomUUID().toString();
        // 获取带点的文件后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        Calendar calendar = Calendar.getInstance();
        String date = new  SimpleDateFormat("yyyy/MMdd").format(new Date());
        // 拼接项目默认文件地址和日期uuid得到保存后的文件名
        String saveFileName = proFileConfig.getFilePath()+"/"+date+"/"+uuid+suffix;
        File desFile = new File(saveFileName);
        if(!desFile.getParentFile().exists()){
            // 创建路径
            boolean mkdirs = desFile.mkdirs();
        }
        try {
            file.transferTo(desFile);
        } catch (IOException e) {
            throw e;
        }
        ProFile proFile = new ProFile();
        proFile.setAdmin(ShiroUtils.getCurrAdmin());
        proFile.setName(uuid+suffix);
        proFile.setPath(proFileConfig.getFileVirtualPath()+"/"+date+"/");
        proFile.setOriginal(fileName);
        proFile.setFileSize(file.getSize());
        return this.addData(proFile);
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
     * @param maxSize example :10MB
     * @return
     */
    public Boolean isTooLong(Long size,String maxSize){
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

    public Boolean isSupport(String fileName){
        for (String suffix:proFileConfig.getSupportFileType()) {
            if (fileName.endsWith(suffix)){
                return true;
            }
        }
        return false;
    }
}
