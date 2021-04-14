package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.ProFile;
import com.duzhuo.wansystem.service.base.ProFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:52
 */
@Slf4j
@Api(tags = "文件模块")
@Controller
@RequestMapping("/base/proFile")
public class ProFileController extends BaseController{
    @Resource
    private ProFileService proFileService;

    @Log(title = "文件列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "文件管理--列表")
    public String list(HttpServletRequest request,CustomSearch<ProFile> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPagedata(proFileService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "/base/file/list";
    }


    @GetMapping("/downLoad")
    @Log(title = "文件下载",operateType = OperateType.DOWLOAD)
    @ApiOperation(value = "文件下载",httpMethod = "GET")
    public ResponseEntity<byte[]> downLoad(HttpServletRequest request, HttpServletResponse response,@NotNull Long id) throws IOException {
        return proFileService.downLoad(id,response);
    }

    @PostMapping("/upload")
    @ResponseBody
    @Log(title = "文件上传",operateType = OperateType.UPLOAD)
    @ApiOperation(value = "文件上传")
    public Message upload(MultipartFile file, @RequestParam(value = "status",defaultValue = "DEFAULT")ProFile.Status status) throws IOException, NoSuchAlgorithmException {
        return Message.success("上传成功！",proFileService.upload(file,status));
    }

    @PostMapping("/uploads")
    @ResponseBody
    @Log(title = "多文件上传",operateType = OperateType.UPLOAD)
    @ApiOperation(value = "多文件上传")
    public Message upload(MultipartFile[] files, @RequestParam(value = "status",defaultValue = "DEFAULT")ProFile.Status status) throws IOException, NoSuchAlgorithmException {
        if (files==null || files.length==0){
            return Message.warn("请先选择文件！");
        }
        for (MultipartFile file:files) {
            proFileService.upload(file,status);
        }
        return Message.success("上传成功！");
    }

    @GetMapping("/findById")
    @ResponseBody
    @Log(title = "查询文件对象",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询文件对象")
    public Message findById(@NotNull Long id){
        return Message.success(proFileService.find(id));
    }

    @ApiOperation(value = "文件IO")
    @GetMapping("/file/{id}")
    public void file(@PathVariable Long id,HttpServletResponse response) throws IOException {
        proFileService.fileIO(id,response);
    }

    @ResponseBody
    @ApiOperation(value = "world转pdf")
    @Log(title = "world转pdf",operateType = OperateType.SELECT)
    @GetMapping("/toPdf")
    public Message toPdf(Long id) throws Exception {
        return Message.success("转换成功，是否立即下载",proFileService.toPdf(id).getId());
    }
}
