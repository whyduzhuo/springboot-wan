package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:54
 */

@Controller
@RequestMapping("/base/role")
@Api(tags = "职务管理模块")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @Log(title = "新增职务",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增职务")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Role role){
        return roleService.addData(role);
    }

    @Log(title = "修改职务",operateType = OperateType.UPDATE)
    @ApiOperation("修改职务")
    @PutMapping("/edit")
    @ResponseBody
    public Message edit(Role role){
        return roleService.edit(role);
    }

    @Log(title = "删除职务",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除职务")
    @DeleteMapping("/del")
    @ResponseBody
    public Message del(@NotNull Long id){
        roleService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个职务",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个职务")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        Role role = roleService.find(id);
        return Message.success(role);
    }

    @Log(title = "测试",operateType = OperateType.OTHER)
    @GetMapping("/test")
    @ApiOperation(value = "测试")
    @ResponseBody
    public Message test(Long roleId,Long adminId){
        return Message.success(roleService.hasRole(roleId,adminId).toString());
    }

}
