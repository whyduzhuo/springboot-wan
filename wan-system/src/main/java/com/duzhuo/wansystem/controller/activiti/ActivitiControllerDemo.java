package com.duzhuo.wansystem.controller.activiti;

import com.duzhuo.common.core.Message;
import org.activiti.engine.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.ibatis.logging.jdbc.BaseJdbcLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/2/26 15:25
 */
@RequestMapping("/activiti")
@Controller
public class ActivitiControllerDemo {

    /**
     * 流程引擎，一下service的bean创建都用到了ProcessEngine
     * activiti核心
     */
    @Autowired
    private ProcessEngine processEngine;
    /**
     * 流程管理定义 服务类
     * ACT_GE_BYTEARRAY 字节文件表
     * ACT_RE_DEPLOYMENT 流程部署表
     * ACT_RE_MODEL
     * ACT_RE_PROCDEF 流程定义表
     */
    @Autowired
    private RepositoryService repositoryService;
    /**
     * 流程运行服务类
     * ACT_RU_EVENT_SUBSCR
     * ACT_RU_EXECUTION
     * ACT_RU_IDENTITYLINK
     * ACT_RU_JOB
     * ACT_RU_TASK
     * ACT_RU_VARIABLE
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 任务管理服务类
     */
    @Autowired
    private TaskService taskService;
    /**
     * 历史流程服务类
     * ACT_HI_ACTINST
     * ACT_HI_ATTACHMENT
     * ACT_HI_COMMENT
     * ACT_HI_DETAIL
     * ACT_HI_IDENTITYLINK
     * ACT_HI_PROCINST
     * ACT_HI_TASKINST
     * ACT_HI_VARINST
     */
    @Autowired
    private HistoryService historyService;
    /**
     * 用户身份服务类
     * ACT_ID_GROUP
     * ACT_ID_INFO
     * ACT_ID_MEMBERSHIP
     * ACT_ID_USER
     */
    @Autowired
    private IdentityService identityService;
    /**
     * 管理器
     */
    @Autowired
    private ManagementService managementService;
    /**
     * 页面表单服务【不常用】
     */
    @Autowired
    private FormService formService;
    /**
     *
     */
    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * {@link ProcessDefinition} 表 ACT_RE_PROCDEF 流程定义 ：如请假流程
     * {@link ProcessInstance} 流程实例 ACT_RU_EXECUTION
     * {@link Execution} 流程执行实例 ACT_RU_EXECUTION，没有并发的情况下，流程执行实例 和 流程执行实例 一样
     * {@link TaskInfo} 任务实例 ACT_RU_TASk
     *
     */


    @GetMapping("/ceshi")
    @ResponseBody
    public Message ceshi(){
        /**
         * 获取流程定义
         */
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition("id");
        /**
         * 获取流程实例 根据流程实例id
         */
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("insId").singleResult();
        /**
         * 获取某人的任务
         */
        List<Task> list = taskService.createTaskQuery().taskAssignee("1200402570").orderByTaskCreateTime().desc().list();

        return Message.success("");
    }

    /**
     * 流程部署
     * @return
     */
    @GetMapping("/deployProcess")
    @ResponseBody
    public Message deployProcess(){
        repositoryService.createDeployment().name("请假流程Demo")
                .addClasspathResource("bpmn/leave.bpmn").deploy();
        return Message.success("流程部署成功！");
    }



}
