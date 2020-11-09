package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.enums.YesOrNo;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.Tools;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.SysOperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.tools.Tool;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

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

    @GetMapping("/list")
    @RequiresPermissions("1001")
    @ApiOperation(value = "日志列表")
    public String list(HttpServletRequest request,CustomSearch<SysOperLog> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        if (Tools.vaildeParam(searchParams.get("eq_status"))){
            searchParams.put("eq_status",YesOrNo.valueOf(searchParams.get("eq_status").toString()));
        }
        if (Tools.vaildeParam(searchParams.get("eq_haveException"))){
            searchParams.put("eq_haveException",YesOrNo.valueOf(searchParams.get("eq_haveException").toString()));
        }
        customSearch.setPagedata(sysOperLogService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams,SEARCH_PREFIX));
        model.addAttribute("yesOrNotList", YesOrNo.values());
        model.addAttribute("methodList",RequestMethod.values());
        return "/base/sysOperLog/list";
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
    @DeleteMapping("/delete")
    @ResponseBody
    public Message del(@NotNull Long id){
        sysOperLogService.delete(id);
        return Message.success("删除成功！");
    }

    @ApiOperation(value = "获取单个日志")
    @GetMapping("/find")
    @ResponseBody
    public Message find(@NotNull Long id){
        return Message.success(sysOperLogService.find(id));
    }

    @PostMapping("/importData")
    @ResponseBody
    public Message importData(MultipartFile file) throws IOException {
        return sysOperLogService.importData(file);
    }

}
