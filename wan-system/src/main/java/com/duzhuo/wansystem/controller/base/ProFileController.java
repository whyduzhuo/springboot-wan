package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.service.base.ProFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author: wanhy
 * @date: 2020/1/2 8:52
 */
@Api(tags = "文件模块")
@Controller
@RequestMapping("/base/proFile")
public class ProFileController {
    @Resource
    private ProFileService proFileService;

    @GetMapping("/uploadTest")
    public String uploadTest(){
        return "base/file/uploadTest";
    }

    @GetMapping("/downLoad/{id}")
    @Log(title = "文件下载",operateType = OperateType.DOWLOAD)
    @ApiOperation(value = "文件下载",httpMethod = "GET")
    public void downLoad(HttpServletRequest request, HttpServletResponse response,@NotNull Long id)throws IOException {
        proFileService.downLoad(id,response);
    }

    @PostMapping("/upload")
    @ResponseBody
    @Log(title = "文件上传",operateType = OperateType.UPLOAD)
    @ApiOperation(value = "文件上传")
    public Message upload(@NotNull MultipartFile file) throws IOException{
        return proFileService.upload(file);
    }

    @PostMapping("/uploads")
    @ResponseBody
    @Log(title = "多文件上传",operateType = OperateType.UPLOAD)
    @ApiOperation(value = "多文件上传")
    public Message upload(@NotEmpty MultipartFile[] files) throws IOException{
        for (MultipartFile file:files) {
            proFileService.upload(file);
        }
        return Message.success("上传成功！");
    }

}
