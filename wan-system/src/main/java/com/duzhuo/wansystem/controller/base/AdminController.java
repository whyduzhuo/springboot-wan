package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.manager.AsyncManager;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.service.base.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: wanhy
 * @date: 2020/1/2 8:52
 */
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/base/admin")
public class AdminController extends BaseController{
    @Resource
    private AdminService adminService;

    @Log(title = "用户列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    public String list(HttpServletRequest request, CustomSearch<Admin> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPagedata(adminService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "/base/admin/list";
    }

    @Log(title = "新增用户",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增用户")
    @PostMapping("/insert")
    @RequiresPermissions("")
    @ResponseBody
    public Message insert(Admin admin){
        return adminService.insert(admin);
    }

    @Log(title = "修改用户信息",operateType = OperateType.UPDATE)
    @ApiOperation("修改用户信息")
    @PutMapping("/edit")
    @RequiresPermissions("")
    @ResponseBody
    public Message edit(Admin admin){
        return adminService.edit(admin);
    }

    @Log(title = "删除用户",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/del")
    @RequiresPermissions("")
    @ResponseBody
    public Message del(Long id){
        adminService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个用户",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个用户")
    @GetMapping("/find")
    @ResponseBody
    public Message find(Long id) {
        return Message.success(adminService.find(id));
    }

}
