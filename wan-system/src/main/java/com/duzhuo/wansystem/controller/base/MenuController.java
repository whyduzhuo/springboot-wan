package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.service.base.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:54
 */

@Controller
@RequestMapping("/base/menu")
@Api(tags = "菜单管理模块")
public class MenuController extends BaseController {
    @Resource
    private MenuService menuService;

    @Log(title = "新增菜单",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增菜单")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Menu menuVO){
        return menuService.addData(menuVO);
    }

    @Log(title = "修改菜单",operateType = OperateType.UPDATE)
    @ApiOperation("修改菜单")
    @PutMapping("/edit")
    @ResponseBody
    public Message edit(Menu menu){
        return menuService.edit(menu);
    }


    @Log(title = "删除菜单",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/del")
    @ResponseBody
    public Message del(Long id){
        menuService.delete(id);
        return Message.success("删除成功！");
    }


    @Log(title = "删除菜单",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/delete")
    @ResponseBody
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
