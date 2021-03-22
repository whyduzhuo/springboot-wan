package com.duzhuo.wansystem.controller.quartz;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.quartz.SysJobLog;
import com.duzhuo.wansystem.service.quartz.SysJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/1/23 16:00
 */
@Api(tags = "定时任务日志")
@Controller
@RequestMapping("/quartz/joblog")
public class SysJobLogController extends BaseController{

    @Resource
    private SysJobLogService sysJobLogService;


    @Log(title = "定时任务日志--列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    @ApiOperation(value = "定时任务日志--列表")
    public String list(HttpServletRequest request, CustomSearch<SysJobLog> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        super.searchParamsTrim(searchParams);
        customSearch.getOrders().add(Sort.Order.desc(BaseEntity.CREATE_DATE_PROPERTY_NAME));
        customSearch.setPagedata(sysJobLogService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/quartz/joblog/list";
    }
}
