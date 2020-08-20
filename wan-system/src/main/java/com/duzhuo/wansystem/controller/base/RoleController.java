package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.RoleService;
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
import java.util.List;
import java.util.Map;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:54
 */

@Controller
@RequestMapping("/base/role")
@Api(tags = "角色管理模块")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    @Log(title = "角色列表",operateType = OperateType.SELECT)
    @ApiOperation("角色列表")
    @RequiresPermissions("1003")
    @GetMapping("/list")
    public String list(CustomSearch<Role> customSearch, Model model, HttpServletRequest request){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPagedata(roleService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "/base/role/list";
    }

    @Log(title = "新增角色窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "新增角色窗口")
    @RequiresPermissions("100300")
    @GetMapping("/addWin")
    public String addWin(Model model){
        Role role = new Role();
        model.addAttribute("data",role);
        return "/base/role/edit";
    }

    @Log(title = "编辑角色窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "编辑角色窗口")
    @RequiresPermissions("100301")
    @GetMapping("/editWin")
    public String editWin(Long id,Model model){
        Role role = roleService.find(id);
        model.addAttribute("data",role);
        return "/base/role/edit";
    }

    @Log(title = "角色管理--用户查看",operateType = OperateType.SELECT)
    @ApiOperation(value = "角色管理--用户查看")
    @RequiresPermissions("100304")
    @GetMapping("/showAdmin")
    public String showAdmin(Long id,Model model){
        Role role = roleService.find(id);
        List<Admin> adminList = role.getAdminList();
        model.addAttribute("data",adminList);
        return "/base/role/showAdmin";
    }

    @Log(title = "角色管理--查询菜单",operateType = OperateType.SELECT)
    @ApiOperation(value = "角色管理--查询菜单")
    @GetMapping("/showMenu")
    @RequiresPermissions("100305")
    public String showMenu(Long id,Model model){
        Role role = roleService.find(id);
        model.addAttribute("data",role);
        return "/base/role/showMenus";
    }

    @Log(title = "角色管理--查询菜单",operateType = OperateType.SELECT)
    @ApiOperation(value = "角色管理--查询菜单")
    @GetMapping("/getMenuTree")
    @ResponseBody
    public Message getMenuTree(Long id){
        return Message.success(roleService.getMenuTree(id));
    }

    @Log(title = "菜单授权",operateType = OperateType.UPDATE)
    @ApiOperation("菜单授权")
    @PostMapping("/grantMenu")
    @RequiresPermissions("100303")
    @ResponseBody
    public Message grantMenu(Long roleId,@RequestParam(value = "menus[]")Long[] menus){
        roleService.grantMenu(roleId,menus);
        return Message.success("保存成功！");
    }

    @Log(title = "新增角色",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增角色")
    @RequiresPermissions("100300")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Role role){
        return roleService.addData(role);
    }

    @Log(title = "修改角色",operateType = OperateType.UPDATE)
    @ApiOperation("修改角色")
    @RequiresPermissions("100301")
    @PutMapping("/edit")
    @ResponseBody
    public Message edit(Role role){
        return roleService.edit(role);
    }

    @Log(title = "删除角色",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/del")
    @RequiresPermissions("100302")
    @ResponseBody
    public Message del(@NotNull Long id){
        roleService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个角色",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个角色")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        Role role = roleService.find(id);
        return Message.success(role);
    }


}
