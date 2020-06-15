package com.duzhuo.wansystem.controller.daily;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.daily.Task;
import com.duzhuo.wansystem.service.daily.TaskService;
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
 * @author: wanhy
 * @date: 2020/5/19 18:06
 */
@Api(tags = "任务")
@Controller
@RequestMapping("/daily/task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @ApiOperation(value = "")
    @Log(title ="",operateType=OperateType.SELECT)
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<Task> customSearch){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = getParametersStartingWith(request,"search_");
        customSearch.getOrders().add(new Sort.Order(Sort.Direction.DESC,"createDate"));
        return "";
    }

    @Log(title ="",operateType = OperateType.INSERT)
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Task taskVO){
        return taskService.addData(taskVO);
    }

    @PostMapping("/detail")
    public String detail(Long id){
        return "";
    }
}
