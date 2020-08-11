package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.DictModel;
import com.duzhuo.wansystem.service.base.DictModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 字典模块--Controller
 * @author: wanhy
 * @date: 2020/8/11 11:23
 */
@RequestMapping("/base/dictModel")
@Controller
@Api
public class DictModelController extends BaseController {

    private DictModelService dictModelService;

    @ApiOperation(value = "字典模块--页面")
    @Log(title = "字典模块--页面",operateType = OperateType.SELECT)
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<DictModel> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,"search_");
        customSearch.setPagedata(dictModelService.search(searchParams,customSearch));
        super.searchParamsTrim(searchParams);
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",searchParams);
        return "";
    }

    @ApiOperation(value = "字典模块--详情")
    @Log(title = "字典模块--详情",operateType = OperateType.SELECT)
    @GetMapping("/detail")
    public String detail(Long id,Model model){
        DictModel dictModel = dictModelService.find(id);
        model.addAttribute("data",model);
        return "";
    }

    @ApiOperation(value = "字典模块--新增/修改")
    @Log(title = "字典模块--新增/修改",operateType = OperateType.INSERT)
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Message saveOrUpdate(DictModel dictModelVO,boolean autoCode){
        if (dictModelVO.getId()==null){
            if (autoCode){
                dictModelVO.setModelCode(dictModelService.getNewCode());
            }
            return dictModelService.addData(dictModelVO);
        }
        return dictModelService.edit(dictModelVO);
    }

}
