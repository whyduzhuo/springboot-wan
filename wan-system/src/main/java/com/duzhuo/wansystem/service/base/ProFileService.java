package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.config.ProFileConfig;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.core.del.DeleteService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.Tools;
import com.duzhuo.common.utils.WordToPDF;
import com.duzhuo.wansystem.dao.base.ProFileDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 项目文件
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:51
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProFileService extends DeleteService<ProFile,Long> {
    @Resource
    private ProFileConfig proFileConfig;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private ProFileDao proFileDao;

    @Resource
    public void setBaseDao(ProFileDao proFileDao){
        super.setBaseDao(proFileDao);
    }

    @Override
    public Page<ProFile> search(Map<String, Object> searchParams, CustomSearch<ProFile> customSearch) {
        Page<ProFile> search = super.search(searchParams, customSearch);
        search.getContent().forEach(r-> r.setExit(this.exit(r)));
        return search;
    }

    public ResponseEntity<byte[]> downLoad(@NotNull Long id, HttpServletResponse response) throws IOException{
        ProFile proFile = super.find(id);
        if (proFile==null){
            throw new FileNotFoundException("文件不存在！");
        }
        HttpHeaders headers = new HttpHeaders();
        String fileName = proFile.getOriginal();
        //设置文件名
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO8859-1"));
        // 文件绝对路径
        String file = this.getAbsolutePath(proFile);
        //把文件转成字节数组
        File byteFile = new File(file);
        int size = (int) byteFile.length();
        FileInputStream inputStream = new FileInputStream(byteFile);
        byte[] bytes = new byte[size];
        int offset=0;
        int readed;
        while(offset<size && (readed = inputStream.read(bytes, offset,inputStream.available() )) != -1){
            offset+=readed;
        }
        inputStream.close();
        //返回
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private String getAbsolutePath(ProFile proFile) {
        return proFileConfig.getFilePath()+proFile.getDownloadPath().substring(proFileConfig.getFileVirtualPath().length(),proFile.getDownloadPath().length());
    }



    /**
     * 判断文件是否存在
     * @param proFile
     * @return
     */
    public Boolean exit(ProFile proFile){
        String absolutePath = getAbsolutePath(proFile);
        File file = new File(absolutePath);
        return file.exists();
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    public ProFile upload(MultipartFile file,ProFile.Status status) throws IOException, NoSuchAlgorithmException {
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
        byte[] bytes = file.getBytes();
        String md5 = Tools.getMD5(bytes);
        // 获取带点的文件后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        ProFile p = this.find(file.getSize(),md5,status);
        String date = new  SimpleDateFormat("yyyy/MMdd").format(new Date());
        if (p!=null){
            ProFile proFile = new ProFile();
            proFile.setAdmin(ShiroUtils.getCurrAdmin());
            proFile.setUuid(p.getUuid());
            proFile.setSuffix(suffix);
            proFile.setPath(p.getPath());
            proFile.setOriginal(fileName.replace(suffix,""));
            proFile.setFileSize(file.getSize());
            proFile.setMd5(md5);
            proFile.setStatus(status);
            this.addData(proFile);
            return proFile;

        }
        // 获取UUID
        String uuid = UUID.randomUUID().toString();
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
            log.error(e.getMessage(),e);
            throw e;
        }
        ProFile proFile = new ProFile();
        proFile.setAdmin(ShiroUtils.getCurrAdmin());
        proFile.setUuid(uuid);
        proFile.setSuffix(suffix);
        proFile.setPath(proFileConfig.getFileVirtualPath()+"/"+date+"/");
        proFile.setOriginal(fileName);
        proFile.setFileSize(file.getSize());
        proFile.setMd5(md5);
        proFile.setStatus(status);
        this.addData(proFile);
        return proFile;
    }

    /**
     * 保存上传记录
     * @param proFileVO
     * @return
     */
    public void addData(ProFile proFileVO){
        ProFile proFile=super.save(proFileVO);
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

    /**
     *
     * @param fileSize
     * @param md5
     * @return
     */
    public ProFile find(Long fileSize,String md5,ProFile.Status status){
        return proFileDao.findByFileSizeAndMd5AndStatus(fileSize,md5,status);
    }

    /**
     *
     * @param id
     * @return
     */
    public File getFile(Long id) {
        ProFile proFile = super.find(id);
        String absolutePath = this.getAbsolutePath(proFile);
        return new File(absolutePath);
    }

    /**
     * 将文件IO写入 response
     * 浏览器会自动打开或者下载
     * @param id
     * @param response
     * @throws IOException
     */
    public void fileIO(Long id, HttpServletResponse response) throws IOException {
        File file = this.getFile(id);
        try (ServletOutputStream outputStream = response.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(file)){
            int len;
            byte[] buffer = new byte[2048];
            while ((len = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            fileInputStream.close();
            outputStream.close();
            response.setHeader("Content-Type", "application/octet-stream");
        }
    }

    /**
     * world 转 pdf
     * @param id
     */
    public ProFile toPdf(Long id) throws Exception {
        ProFile proFile = super.find(id);
        if (!proFile.getSuffix().equalsIgnoreCase(".doc") && !proFile.getSuffix().equalsIgnoreCase(".docx")){
            throw new ServiceException("非pdf文件！");
        }
        String date = new  SimpleDateFormat("yyyy/MMdd").format(new Date());
        String uuid = UUID.randomUUID().toString();
        String suffix = ".pdf";
        String newFilePath = proFileConfig.getFilePath()+"/"+date+"/"+uuid+suffix;
        //world绝对路径
        String wordFile = this.getAbsolutePath(proFile);
        String pdfFile = proFileConfig.getFilePath()+"/"+date+"/"+uuid+suffix;
        WordToPDF.toPdf(wordFile,pdfFile);
        ProFile pdfProfile = new ProFile();
        pdfProfile.setAdmin(ShiroUtils.getCurrAdmin());
        File filePdf = new File(pdfFile);
        pdfProfile.setFileSize(filePdf.length());

        byte[] bytes = FileUtils.readFileToByteArray(filePdf);
        String md5 = Tools.getMD5(bytes);
        pdfProfile.setMd5(md5);
        pdfProfile.setOriginal(proFile.getOriginal());
        pdfProfile.setPath(proFileConfig.getFileVirtualPath()+"/"+date+"/");
        pdfProfile.setStatus(ProFile.Status.PUBLIC);
        pdfProfile.setSuffix(".pdf");
        pdfProfile.setUuid(uuid);
        this.addData(pdfProfile);
        return pdfProfile;
    }
}
