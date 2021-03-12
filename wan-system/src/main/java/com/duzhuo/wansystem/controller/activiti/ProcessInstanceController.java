package com.duzhuo.wansystem.controller.activiti;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.base.BaseController;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.common.utils.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 流程实例
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/6 16:44
 */
@Api(value = "流程实例")
@Controller
@RequestMapping("/activiti/instance")
public class ProcessInstanceController extends BaseController{

    @Autowired
    private HistoryService historyService;


    @RequiresPermissions("1201")
    @ApiModelProperty(value = "流程实例列表")
    @Log(title = "/流程实例列表",operateType = OperateType.SELECT)
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<HistoricProcessInstance> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        PageRequest pageRequest =PageRequest.of(customSearch.getPageNumber() - 1, customSearch.getPageSize());
        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
        if (Tools.vaildeParam(searchParams.get("like_name"))){
            query.processInstanceNameLike("%"+searchParams.get("like_name").toString()+"%");
        }
        List<HistoricProcessInstance> processDefinitions = query
                .orderByProcessInstanceId().asc()
                .listPage((int) pageRequest.getOffset(), pageRequest.getPageSize());
        customSearch.setPagedata(new PageImpl<>(processDefinitions,pageRequest,query.count()));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/activiti/instance/list";
    }
}
