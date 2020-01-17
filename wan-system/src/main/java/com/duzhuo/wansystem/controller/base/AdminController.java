package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.service.base.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author: wanhy
 * @date: 2020/1/2 8:52
 */
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/base/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @Log(title = "新增用户",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增用户")
    @PostMapping("/insert")
    @ResponseBody
    public Message insert(Admin admin){
        return adminService.insert(admin);
    }

    @Log(title = "修改用户信息",operateType = OperateType.UPDATE)
    @ApiOperation("修改用户信息")
    @PutMapping("/edit")
    @ResponseBody
    public Message edit(Admin admin){
        return adminService.edit(admin);
    }

    @Log(title = "删除用户",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/del")
    @ResponseBody
    public Message del(@NotNull Long id){
        adminService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个用户",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个用户")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        Admin admin = adminService.find(id);
        return Message.success(admin);
    }

}
