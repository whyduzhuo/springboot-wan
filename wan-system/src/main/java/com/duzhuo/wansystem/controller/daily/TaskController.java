package com.duzhuo.wansystem.controller.daily;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.daily.Issue;
import com.duzhuo.wansystem.service.daily.IssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.web.util.WebUtils.getParametersStartingWith;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/5/19 18:06
 */
@Api(tags = "任务")
@Controller
@RequestMapping("/daily/issue")
public class TaskController extends BaseController {
    @Resource
    private IssueService issueService;

    @ApiOperation(value = "")
    @Log(title ="",operateType=OperateType.SELECT)
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<Issue> customSearch){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = getParametersStartingWith(request,SEARCH_PREFIX);
        customSearch.getOrders().add(new Sort.Order(Sort.Direction.DESC,"createDate"));
        return "";
    }

    @Log(title ="",operateType = OperateType.INSERT)
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Issue issueVO){
        return issueService.addData(issueVO);
    }

    @PostMapping("/detail")
    public String detail(Long id){
        return "";
    }
}
