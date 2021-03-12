package com.duzhuo.wansystem.controller.activiti;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.activiti.Leave;
import com.duzhuo.wansystem.service.activiti.LeaveService;
import com.duzhuo.wansystem.service.base.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 请假申请--Controller
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/03/11 16:28:18
 */
@Api(value = "请假申请")
@Controller
@RequestMapping("/activiti/leave")
public class LeaveController extends BaseController {

    @Resource
    private LeaveService leaveService;
    @Resource
    private AdminService adminService;

    @RequiresPermissions("120101")
    @Log(title = "请假申请--列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "请假申请--列表")
    public String list(HttpServletRequest request,CustomSearch<Leave> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.setPagedata(leaveService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "/activiti/leave/list";
    }

    @RequiresPermissions("120102")
    @GetMapping("/mylist")
    @Log(title = "请假申请--我的申请",operateType = OperateType.SELECT)
    public String mylist(HttpServletRequest request,CustomSearch<Leave> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.getOrders().add(Sort.Order.desc(BaseEntity.CREATE_DATE_PROPERTY_NAME));
        customSearch.getFilters().add(Filter.eq("admin", adminService.getCurrent()));
        customSearch.setPagedata(leaveService.search(searchParams,customSearch));
        return "/activiti/leave/mylist";
    }

    @RequiresPermissions("120100")
    @Log(title = "新增请假申请窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "新增请假申请窗口")
    @GetMapping("/addWin")
    public String addWin(Model model){
        return "/activiti/leave/addWin";
    }

    @ApiOperation(value = "提交审批")
    @PostMapping("/submitApply")
    @ResponseBody
    public Message submitApply(Long id){
        leaveService.submitApply(id);
        return Message.success("提交成功");
    }

    @RequiresPermissions("120100")
    @Log(title = "新增请假申请",operateType = OperateType.INSERT)
    @ApiOperation(value = "新增请假申请")
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Leave leaveVO){
        leaveVO.setAdmin(adminService.getCurrent());
        leaveService.addData(leaveVO);
        return Message.success("添加成功！");
    }

    @RequiresPermissions("1")
    @Log(title = "编辑请假申请窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "编辑请假申请窗口")
    @GetMapping("/editWin")
    public String editWin(Model model,Long id){
        model.addAttribute("data",leaveService.find(id));
        return "/activiti/leave/detail";
    }

    @RequiresPermissions("1")
    @Log(title = "修改请假申请",operateType = OperateType.UPDATE)
    @ApiOperation(value = "修改请假申请")
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(Leave leaveVO){
        leaveService.edit(leaveVO);
        return Message.success("修改成功！");
    }

    @RequiresPermissions("1")
    @Log(title = "删除请假申请",operateType = OperateType.UPDATE)
    @ApiOperation(value = "删除请假申请")
    @ResponseBody
    @PostMapping("/del")
    public Message del(Long id){
        leaveService.del(id);
        return Message.success("删除成功！");
    }

}
