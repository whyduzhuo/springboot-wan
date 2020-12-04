package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.config.ProFileConfig;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.ProFileDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 项目文件
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ProFileService extends BaseService<ProFile,Long> {

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
        proFile.setUuid(uuid);
        proFile.setSuffix(suffix);
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


    public File saveUrlAs(String url){
        String fileName = url.substring(url.lastIndexOf("path=")+5);
        //创建不同的文件夹目录
        File file=new File(proFileConfig.getFilePath()+"/"+fileName);
        //判断文件是否存在
        if (file.exists()){
            return file;
        }
        //判断文件夹是否存在
        if (!file.getParentFile().exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try
        {
            // 建立链接
            URL httpUrl=new URL(url);
            conn=(HttpURLConnection) httpUrl.openConnection();
            //以Post方式提交表单，默认get方式
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(proFileConfig.getFilePath()+"/"+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return file;
    }

    public File wordToPdF(File wordFile){
        String wordFileName = wordFile.getName();
        String suffix = wordFileName.substring(wordFileName.lastIndexOf(".")+1);
        if ("PDF".equalsIgnoreCase(suffix)){
            return wordFile;
        }
        if ("docx".equalsIgnoreCase(suffix) || "doc".equalsIgnoreCase(suffix)){

        }
        System.err.println(wordFileName);
        return null;
    }
}
