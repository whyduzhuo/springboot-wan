package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.RedisUtils;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
 * @author: wanhy
 * @date: 2020/1/2 8:52
 */
@Api(tags = "用户管理模块")
@Controller
@RequestMapping("/base/admin")
public class AdminController extends BaseController{
    @Resource
    private AdminService adminService;
    @Resource
    private MenuService menuService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RoleService roleService;


    @Log(title = "用户列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "用户列表")
    public String list(HttpServletRequest request, CustomSearch<Admin> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        customSearch.getOrders().add(Sort.Order.asc("isDelete"));
        customSearch.setPagedata(adminService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/base/admin/list";
    }

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
        return adminService.addData(adminVO);
    }

    @Log(title = "修改用户信息",operateType = OperateType.UPDATE)
    @ApiOperation("修改用户信息")
    @PutMapping("/edit")
    @RequiresPermissions("")
    @ResponseBody
    public Message edit(Admin admin){
        return adminService.edit(admin);
    }

    @Log(title = "禁用/启用用户",operateType = OperateType.UPDATE)
    @ApiOperation(value = "禁用/启用用户")
    @DeleteMapping("/del")
    @RequiresPermissions("100400")
    @ResponseBody
    public Message del(Long id){
        String string = null;
        string.equals("aa");
        return adminService.del(id);
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
        Admin admin = adminService.find(id);
        Set<Role> roleSet = admin.getRoleSet();
        model.addAttribute("admin",admin);
        model.addAttribute("roleList",roleSet);
        return "/base/admin/showMenus";
    }

    @GetMapping("/getMenuTree")
    @ApiOperation("查询admin的所有菜单")
    @ResponseBody
    public Message getMenuTree(@RequestParam("id") Long id){
        List<Menu> allMenuList = menuService.findAll(Sort.by(Sort.Direction.ASC,"order"));
        Admin admin = adminService.find(id);
        Set<Role> roleList = admin.getRoleSet();
        Set<Menu> menuSet = new HashSet<>();
        roleList.forEach(r->menuSet.addAll(r.getMenuSet()));
        List<Ztree> ztreeList = menuService.buildSelectMenu(allMenuList,menuSet);
        return Message.success(ztreeList);
    }

    @ApiOperation(value = "查询用户下每个角色的菜单")
    @GetMapping("/getRolesMenu")
    @ResponseBody
    public Message getRolesMenu(Long adminId){
        Admin admin = adminService.find(adminId);
        Set<Role> roleSet = admin.getRoleSet();
        List<Ztree> ztreeList = roleService.findMenuTree(roleSet);
        return Message.success(ztreeList);
    }

    @GetMapping("/test")
    @ResponseBody
    public Admin test(){
        Admin admin = ShiroUtils.getCurrAdmin();
        redisUtils.set("hehe",admin,200);
        return redisUtils.get("hehe",Admin.class);
    }
}
