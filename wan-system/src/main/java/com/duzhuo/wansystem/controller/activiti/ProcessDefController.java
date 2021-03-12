package com.duzhuo.wansystem.controller.activiti;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.Tools;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 流程定义
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/6 15:29
 */
@Controller
@RequestMapping("/activiti/processDef")
public class ProcessDefController extends BaseController{

    @Resource
    private RepositoryService repositoryService;

    @RequiresPermissions("1200")
    @ApiModelProperty(value = "流程定义列表")
    @Log(title = "/流程定义列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<ProcessDefinition> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        PageRequest pageRequest =PageRequest.of(customSearch.getPageNumber() - 1, customSearch.getPageSize());
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (Tools.vaildeParam(searchParams.get("like_name"))){
            processDefinitionQuery.processDefinitionNameLike("%"+searchParams.get("like_name").toString()+"%");
        }
        if (Tools.vaildeParam(searchParams.get("eq_status"))){
            if ("1".equalsIgnoreCase(searchParams.get("eq_status").toString())){
                processDefinitionQuery.active();
            }else {
                processDefinitionQuery.suspended();
            }
        }

        List<ProcessDefinition> processDefinitions = processDefinitionQuery
                .orderByProcessDefinitionId().asc()
                .listPage((int) pageRequest.getOffset(), pageRequest.getPageSize());
        customSearch.setPagedata(new PageImpl<>(processDefinitions,pageRequest,processDefinitionQuery.count()));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/activiti/processDef/list";
    }

    @RequiresPermissions("120000")
    @ApiOperation(value = "流程暂停/激活",notes = "1激活流程，0暂停流程。暂停之后不可用启动新的流程实例")
    @Log(title = "流程暂停/激活",operateType = OperateType.UPDATE)
    @ResponseBody
    @PostMapping("/changeSuspended")
    public Message changeSuspended(String id,String status){
        if ("1".equalsIgnoreCase(status)){
            repositoryService.activateProcessDefinitionById(id);
        }else {
            repositoryService.suspendProcessDefinitionById(id);
        }
        return Message.success("操作成功！");
    }

    /**
     * 图片查看
     * @param procDefId 流程定义id
     * @param response
     * @throws IOException
     */
    @GetMapping("/img")
    public void img(String procDefId,HttpServletResponse response) throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        // 图片文件名称
        String resourceName = processDefinition.getDiagramResourceName();
        // 流程部署id 和资源名称
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * XML查看
     * @param procDefId
     * @param response
     */
    @GetMapping("/bpmn")
    public void bpmn(String procDefId,HttpServletResponse response) throws IOException {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
        // bpmn文件名称
        String resourceNameBpmn = processDefinition.getResourceName();
        // 流程部署id 和资源名称
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceNameBpmn);
        byte[] b = new byte[1024];
        int len;
        while ((len = inputStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
}
