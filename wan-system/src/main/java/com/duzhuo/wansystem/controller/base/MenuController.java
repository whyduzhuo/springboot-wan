package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:54
 */

@Controller
@RequestMapping("/base/menu")
@Api(tags = "菜单管理模块")
public class MenuController extends BaseController {
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

    @RequiresPermissions("1002")
    @ApiOperation(value = "菜单列表")
    @Log(title = "菜单列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    public String list(){
        return "/base/menu/list";
    }

    @RequiresPermissions("1002")
    @ApiOperation(value = "菜单数据")
    @Log(title = "菜单数据",operateType = OperateType.SELECT)
    @GetMapping("/getNode")
    @ResponseBody
    public Message getNode(){
        List<Menu> menus = menuService.findAll(Sort.by(Sort.Direction.ASC,"order"));
        List<Ztree> data = menuService.toTree(menus);
        return Message.success(data);
    }

    @ApiOperation(value = "菜单新增页面")
    @Log(title = "菜单新增页面",operateType = OperateType.SELECT)
    @RequiresPermissions("100200")
    @GetMapping("/addWin")
    public String addWin(Model model,Long pid){
        if (pid==null){
            throw new ServiceException("请先选择一个节点");
        }
        Menu parent = menuService.find(pid);
        Menu menu = new Menu();
        menu.setParent(parent);
        menu.setOrder(menuService.getMaxOrder(parent.getId())+1);
        menu.setNum(menuService.createId(parent));
        model.addAttribute("typeList",Menu.TypeEnum.values());
        model.addAttribute("data",menu);
        return "/base/menu/edit";
    }

    @Log(title = "新增菜单",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增菜单")
    @RequiresPermissions("100200")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Menu menuVO){
        menuService.addData(menuVO);
        return Message.success("添加成功！",menuService.toTree(menuVO));
    }

    @Log(title = "新增菜单",operateType = OperateType.SELECT)
    @ApiOperation("修改菜单页面")
    @RequiresPermissions("100201")
    @GetMapping("/editWin")
    public String editWin(Model model,Long id){
        Menu menu = menuService.find(id);
        model.addAttribute("typeList",Menu.TypeEnum.values());
        model.addAttribute("data",menu);
        return "/base/menu/edit";
    }

    @Log(title = "修改菜单",operateType = OperateType.UPDATE)
    @ApiOperation("修改菜单")
    @RequiresPermissions("100201")
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(Menu menu){
        menuService.edit(menu);
        return Message.success("修改成功！");
    }


    @Log(title = "删除菜单",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/del")
    @ResponseBody
    @RequiresPermissions("100202")
    public Message del(Long id){
        menuService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title = "删除菜单",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/delete")
    @ResponseBody
    @RequiresPermissions("100202")
    public Message del(@RequestParam("ids") Long... ids){
        menuService.delete(ids);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个菜单",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个菜单")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        Menu menu = menuService.find(id);
        return Message.success(menu);
    }
}
