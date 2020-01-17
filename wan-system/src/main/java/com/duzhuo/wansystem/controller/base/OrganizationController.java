package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Organization;
import com.duzhuo.wansystem.service.base.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:55
 */

@Controller
@RequestMapping("/base/organization")
@Api(tags = "部门管理模块")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    @Log(title = "新增部门",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增部门")
    @PostMapping("/insert")
    @ResponseBody
    public Message insert(Organization organization){
        return organizationService.insert(organization);
    }

    @Log(title = "修改部门",operateType = OperateType.UPDATE)
    @ApiOperation("修改部门")
    @PutMapping("/edit")
    @ResponseBody
    public Message edit(Organization organization){
        return organizationService.edit(organization);
    }

    @Log(title = "删除部门",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/del")
    @ResponseBody
    public Message del(@NotNull Long id){
        organizationService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个部门",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个部门")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        Organization organization = organizationService.find(id);
        return Message.success(organization);
    }
}
