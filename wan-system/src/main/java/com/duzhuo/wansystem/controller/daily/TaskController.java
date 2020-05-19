package com.duzhuo.wansystem.controller.daily;

import com.duzhuo.wansystem.service.daily.TaskService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

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

}
