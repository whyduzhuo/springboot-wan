package com.duzhuo.wansystem.controller.quartz;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.exception.TaskException;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.quartz.SysJob;
import com.duzhuo.wansystem.service.quartz.SysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 定时任务--Controller
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/01/21 15:33:12
 */
@Api(tags = "定时任务")
@Controller
@RequestMapping("/quartz/sysjob")
public class SysJobController extends BaseController {

    @Resource
    private SysJobService sysJobService;

    @RequiresPermissions("1010")
    @Log(title = "定时任务--列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "定时任务--列表")
    public String list(HttpServletRequest request, CustomSearch<SysJob> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPagedata(sysJobService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/quartz/sysjob/list";
    }

    @RequiresPermissions("101000")
    @Log(title = "新增定时任务窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "新增定时任务窗口")
    @GetMapping("/addWin")
    public String addWin(Model model){
        model.addAttribute("LogicalEnumList", SysJob.LogicalEnum.values());
        model.addAttribute("StatusEnumList", SysJob.StatusEnum.values());
        model.addAttribute("ConcurrentEnumList", SysJob.ConcurrentEnum.values());
        model.addAttribute("data",new SysJob());
        return "/quartz/sysjob/addWin";
    }

    @RequiresPermissions("101000")
    @Log(title = "新增定时任务",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增定时任务")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(SysJob sysJobVO) throws TaskException, SchedulerException {
        sysJobService.addData(sysJobVO);
        return Message.success("创建成功！");
    }

    @RequiresPermissions("101001")
    @Log(title = "编辑定时任务窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "编辑定时任务窗口")
    @GetMapping("/editWin")
    public String editWin(Model model,Long id){
        model.addAttribute("LogicalEnumList", SysJob.LogicalEnum.values());
        model.addAttribute("StatusEnumList", SysJob.StatusEnum.values());
        model.addAttribute("ConcurrentEnumList", SysJob.ConcurrentEnum.values());
        model.addAttribute("data",sysJobService.find(id));
        return "/quartz/sysjob/editWin";
    }

    @RequiresPermissions("101001")
    @Log(title = "修改时任务",operateType = OperateType.UPDATE)
    @ApiOperation(value = "修改时任务")
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(SysJob sysJobVO){
        sysJobService.edit(sysJobVO);
        return Message.success("修改成功");
    }


    @RequiresPermissions("1")
    @Log(title = "删除定时任务",operateType = OperateType.UPDATE)
    @ApiOperation(value = "删除定时任务")
    @ResponseBody
    @DeleteMapping("/del")
    public Message del(Long id) throws SchedulerException {
        sysJobService.deleteJob(id);
        return Message.success("删除成功");
    }

    @PostMapping("/resumeJob")
    @RequiresPermissions("101002")
    @Log(title = "启动任务",operateType = OperateType.UPDATE)
    @ApiOperation(value = "启动任务")
    @ResponseBody
    public Message resumeJob(Long id) throws SchedulerException {
        sysJobService.resumeJob(id);
        return Message.success("启动成功！");
    }

    @PostMapping("/pauseJob")
    @RequiresPermissions("101003")
    @Log(title = "暂停任务",operateType = OperateType.UPDATE)
    @ApiOperation(value = "暂停任务")
    @ResponseBody
    public Message pauseJob(Long id) throws SchedulerException {
        sysJobService.stopJob(id);
        return Message.success("暂停成功！");
    }

    @RequiresPermissions({"101002","101003"})
    @Log(title = "修改任务状态",operateType = OperateType.UPDATE)
    @ApiOperation(value = "修改任务状态")
    @ResponseBody
    @PostMapping("/changeStatus")
    public Message changeStatus(Long id) throws SchedulerException {
        sysJobService.changeStatus(id);
        return Message.success("修改成功！");
    }

    @PostMapping("/run")
    @Log(title = "任务触发",operateType = OperateType.OTHER)
    @ApiOperation(value = "任务触发")
    @ResponseBody
    public Message run(Long id) throws SchedulerException {
        sysJobService.run(id);
        return Message.success("触发成功");
    }
}
