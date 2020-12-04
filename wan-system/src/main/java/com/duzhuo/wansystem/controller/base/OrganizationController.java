package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Organization;
import com.duzhuo.wansystem.service.base.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:55
 */

@Controller
@RequestMapping("/base/organization")
@Api(tags = "部门管理模块")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    @RequiresPermissions("1007")
    @ApiOperation(value = "部门列表")
    @Log(title = "部门列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    public String list(){
        return "/base/organization/list";
    }

    @RequiresPermissions("1007")
    @ApiOperation(value = "部门数据")
    @Log(title = "部门数据",operateType = OperateType.SELECT)
    @GetMapping("/getNode")
    @ResponseBody
    public Message getNode(){
        List<Ztree> data = organizationService.getAllEnableTree();
        return Message.success(data);
    }

    @RequiresPermissions("100700")
    @Log(title = "新增部门",operateType = OperateType.SELECT)
    @ApiOperation(value = "新增部门")
    @GetMapping("addWin")
    public String addWin(Model model,Long pid){
        Organization p = organizationService.find(pid);
        Organization organization = new Organization();
        organization.setParent(p);
        organization.setOrder(organizationService.getMaxOrder(pid)+1);
        model.addAttribute("data",organization);
        return "/base/organization/edit";
    }

    @RequiresPermissions("100700")
    @Log(title = "新增部门",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增部门")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Organization organization){
        organizationService.addData(organization);
        return Message.success("添加成功!",organizationService.organizationToTree(organization));
    }

    @RequiresPermissions("100700")
    @Log(title = "修改部门",operateType = OperateType.SELECT)
    @ApiOperation(value = "修改部门")
    @GetMapping("editWin")
    public String editWin(Model model,Long id){
        Organization organization = organizationService.find(id);
        if (organization.getType()==Organization.Type.固定节点){
            throw new ServiceException("固定节点,不可修改！");
        }
        model.addAttribute("data",organization);
        return "/base/organization/edit";
    }

    @RequiresPermissions("100701")
    @Log(title = "修改部门",operateType = OperateType.UPDATE)
    @ApiOperation("修改部门")
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(Organization organization){
        organizationService.edit(organization);
        return Message.success("修改成功!");
    }

    @RequiresPermissions("100702")
    @Log(title = "删除部门",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/del")
    @ResponseBody
    public Message del(@NotNull Long id){
        Organization organization = organizationService.find(id);
        if (organization.getType()==Organization.Type.固定节点){
            throw new ServiceException("固定节点,不可删除！");
        }
        if (!organizationService.getAllEnable(id).isEmpty()){
            throw new ServiceException("存在子节点，请先移除子节点！");
        }
        organizationService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个部门",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个部门")
    @GetMapping("/findById")
    @ResponseBody
    public Message findById( @NotNull Long id){
        Organization organization = organizationService.find(id);
        return Message.success(organization);
    }
}
