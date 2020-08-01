package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

import static org.springframework.web.util.WebUtils.getParametersStartingWith;

/**
 * @author: wanhy
 * @date: 2020/1/4 11:33
 */
@Controller
@RequestMapping("/base/sysOperLog")
@Api(tags = "用户操作日志")
public class SysOperLogController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SysOperLogController.class);

    @Resource
    private SysOperLogService sysOperLogService;
    @Resource
    private AdminService adminService;

    @Log(title = "日志列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "日志列表")
    public String list(HttpServletRequest request,CustomSearch<SysOperLog> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = getParametersStartingWith(request,"search_");
        customSearch.setPagedata(sysOperLogService.search(searchParams,customSearch));
        super.searchParamsTrim(searchParams);
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        model.addAttribute("admin", adminService.find(1L));
        model.addAttribute("haha","万宏远");
        return "base/sysOperLog/list";
    }

    @Log(title = "新增日志",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增日志")
    @PostMapping("/insert")
    @ResponseBody
    public Message insert(SysOperLog sysOperLogVO){
        return sysOperLogService.insert(sysOperLogVO);
    }

    @Log(title = "删除日志",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除日志")
    @DeleteMapping("/{id}")
    @ResponseBody
    public Message del(@PathVariable @NotNull Long id){
        sysOperLogService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title = "获取单个日志",operateType = OperateType.SELECT)
    @ApiOperation(value = "获取单个日志")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        return Message.success(sysOperLogService.find(id));
    }

    @PostMapping("/importData")
    @ResponseBody
    public Message importData(MultipartFile file) throws IOException {
        try {
            return sysOperLogService.importData(file);
        }catch (IOException e){
            logger.error("文件导入失败！",e);
            throw e;
        }
    }

}
