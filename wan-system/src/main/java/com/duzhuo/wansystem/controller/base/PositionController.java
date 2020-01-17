package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Position;
import com.duzhuo.wansystem.service.base.PositionService;
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
@RequestMapping("/base/position")
@Api(tags = "职务管理模块")
public class PositionController extends BaseController {

    @Resource
    private PositionService positionService;

    @Log(title = "新增职务",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增职务")
    @PostMapping("/insert")
    @ResponseBody
    public Message insert(Position position){
        return positionService.insert(position);
    }

    @Log(title = "修改职务",operateType = OperateType.UPDATE)
    @ApiOperation("修改职务")
    @PutMapping("/edit")
    @ResponseBody
    public Message edit(Position position){
        return positionService.edit(position);
    }

    @Log(title = "删除职务",operateType = OperateType.DELETE)
    @ApiOperation(value = "删除职务")
    @DeleteMapping("/del")
    @ResponseBody
    public Message del(@NotNull Long id){
        positionService.delete(id);
        return Message.success("删除成功！");
    }

    @Log(title ="获取单个职务",operateType = OperateType.SELECT)
    @ApiOperation(value = "查询单个职务")
    @GetMapping("/{id}")
    @ResponseBody
    public Message findById(@PathVariable @NotNull Long id){
        Position position = positionService.find(id);
        return Message.success(position);
    }
}
