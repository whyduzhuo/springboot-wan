package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.core.del.DeleteEntity;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.RedisUtils;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.entity.base.po.AdminPo;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import com.duzhuo.wansystem.service.base.po.AdminPoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户管理-Controller
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:52
 */
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/base/admin")
public class AdminController extends BaseController {
    @Resource
    private AdminService adminService;
    @Resource
    private AdminPoService adminPoService;
    @Resource
    private MenuService menuService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RoleService roleService;


    @RequiresPermissions("1004")
    @Log(title = "用户列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    public String list(HttpServletRequest request, CustomSearch<AdminPo> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        customSearch.getOrders().add(Sort.Order.asc(DeleteEntity.DEL_TIME_PROPERTY_NAME));
        customSearch.setPagedata(adminPoService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/base/admin/list";
    }

    @RequiresPermissions("100402")
    @Log(title = "用户详情",operateType = OperateType.SELECT)
    @ApiOperation("用户详情")
    @GetMapping("/detail")
    public String detail(@RequestParam("id") Long id,Model model){
        Admin admin = adminService.find(id);
        model.addAttribute("data",admin);
        return "/base/admin/edit";
    }

    @Log(title = "新增用户窗口",operateType = OperateType.SELECT)
    @ApiOperation("新增用户窗口")
    @GetMapping("/addWin")
    @RequiresPermissions("100401")
    public String addWin(){
        return "/base/admin/addWin";
    }

    @Log(title = "新增用户",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增用户")
    @PostMapping("/addData")
    @RequiresPermissions("100401")
    @ResponseBody
    public Message addData(Admin adminVO){
        adminService.addData(adminVO);
        return Message.success("添加成功！");
    }

    @Log(title = "修改用户信息",operateType = OperateType.UPDATE)
    @ApiOperation("修改用户信息")
    @PostMapping("/edit")
    @RequiresPermissions("100402")
    @ResponseBody
    public Message edit(Admin admin){
        adminService.edit(admin);
        return Message.success("修改成功！");
    }

    @Log(title = "禁用/启用用户",operateType = OperateType.UPDATE)
    @ApiOperation(value = "禁用/启用用户")
    @DeleteMapping("/del")
    @RequiresPermissions("100400")
    @ResponseBody
    public Message del(Long id){
        adminService.del(id);
        return Message.success("操作成功！");
    }

    @Log(title ="获取单个用户",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个用户")
    @GetMapping("/find")
    @ResponseBody
    public Message find(Long id) {
        return Message.success(adminService.find(id));
    }

    @Log(title = "查询用户菜单",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询用户菜单")
    @GetMapping("/menuList")
    public String menuList(@RequestParam("id") Long id,Model model){
        AdminPo adminPo = adminPoService.find(id);
        List<Role> roleList = adminPo.getRoleList();
        model.addAttribute("admin",adminPo);
        model.addAttribute("roleList",roleList);
        return "/base/admin/showMenus";
    }

    @Log(title = "查询admin的所有菜单",operateType = OperateType.SELECT)
    @GetMapping("/getMenuTree")
    @ApiOperation("查询admin的所有菜单")
    @ResponseBody
    public Message getMenuTree(@RequestParam("id") Long id){
        List<Menu> allMenuList = menuService.findAll(Sort.by(Sort.Direction.ASC,"order"));
        Admin admin = adminService.find(id);
        List<Menu> menuList = adminService.getMenuList(admin);
        List<Ztree> allMenuListTree = menuService.toTree(allMenuList);
        List<Ztree> menuListTree = menuService.toTree(menuList);
        List<Ztree> result = Ztree.buildTree(allMenuListTree,menuListTree);
        return Message.success(result);
    }

    @Log(title = "查询用户下每个角色的菜单",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询用户下每个角色的菜单")
    @GetMapping("/getRolesMenu")
    @ResponseBody
    public Message getRolesMenu(Long adminId){
        AdminPo adminPo = adminPoService.find(adminId);
        List<Role> roleList = adminPo.getRoleList();
        List<Ztree> ztreeList = roleService.findMenuTree(roleList);
        return Message.success(ztreeList);
    }

    @Log(title = "显示用户的角色",operateType = OperateType.SELECT)
    @ApiOperation("显示用户的角色")
    @GetMapping("/showRoles")
    public String showRoles(Long id,Model model,String name){
        AdminPo adminPo = adminPoService.find(id);
        List<Filter> filters = new ArrayList<>();
        if (StringUtils.isNotBlank(name)){
            filters.add(Filter.eq("name",name));
        }
        List<Role> roleList = roleService.searchList(filters);
        roleList.forEach(r->{
            boolean b = roleService.in(adminPo.getRoleList(),r);
            if (b){
                r.setChecked(true);
            }
        });
        model.addAttribute("roleList",roleList);
        model.addAttribute("admin",adminPo);
        model.addAttribute("name",name);
        return "/base/admin/showRoles";
    }

    @Log(title = "角色授权",operateType = OperateType.UPDATE)
    @ApiOperation("角色授权")
    @RequiresPermissions("100403")
    @PostMapping("grantRoles")
    @ResponseBody
    public Message grantRoles(Long id,@RequestParam(value = "roleIds[]",required = false,defaultValue = "") Long[] roleIds){
        adminService.grantRoles(id,roleIds);
        return Message.success("授予成功！");
    }

    @Log(title = "修改密码",operateType = OperateType.UPDATE)
    @ApiOperation("修改密码")
    @RequiresPermissions("")
    @PostMapping("/resetPassword")
    @ResponseBody
    public Message resetPassword(Long adminId,String password){
        return adminService.resetPassword(adminId,password);
    }
}
