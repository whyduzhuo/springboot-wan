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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 字典模块--Controller
 * @author: 万宏远
 * @date: 2020/8/11 11:23
 */
@RequestMapping("/base/dictModel")
@Controller
@Api
public class DictModelController extends BaseController {

    @Resource
    private DictModelService dictModelService;

    @RequiresPermissions("1005")
    @ApiOperation(value = "字典模块--页面")
    @Log(title = "字典模块--页面",operateType = OperateType.SELECT)
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<DictModel> customSearch, Model model){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        customSearch.getOrders().add(Sort.Order.asc("order"));
        customSearch.setPagedata(dictModelService.search(searchParams,customSearch));
        super.searchParamsTrim(searchParams);
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams,SEARCH_PREFIX));
        return "/base/dictionaryModel/list";
    }

    @RequiresPermissions({"100500","100501"})
    @ApiOperation(value = "字典模块--详情")
    @Log(title = "字典模块--详情",operateType = OperateType.SELECT)
    @GetMapping("/detail")
    public String detail(Long id,Model model){
        DictModel dictModel;
        if (id==null){
            dictModel = new DictModel();
            dictModel.setModelCode(dictModelService.getNewCode());
            dictModel.setOrder(dictModelService.getNewOrder());
        }else{
            dictModel = dictModelService.find(id);
        }
        model.addAttribute("data",dictModel);
        return "/base/dictionaryModel/edit";
    }

    @RequiresPermissions({"100500"})
    @ApiOperation(value = "字典模块--新增")
    @Log(title = "字典模块--新增",operateType = OperateType.INSERT)
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(DictModel dictModelVO){
        dictModelService.addData(dictModelVO);
        return Message.success("添加成功!");
    }

    @RequiresPermissions({"100501"})
    @ApiOperation(value = "字典模块--修改")
    @Log(title = "字典模块--修改",operateType = OperateType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(DictModel dictModelVO){
        dictModelService.edit(dictModelVO);
        return Message.success("修改成功!");
    }

    @RequiresPermissions("100503")
    @ApiOperation(value = "字典--修改排序")
    @PostMapping("/upOrDown")
    @ResponseBody
    @Log(title = "字典--修改排序",operateType = OperateType.UPDATE)
    public Message upOrDown(Long id,Integer change){
        if (change<0){
            dictModelService.down(id);
            return Message.success("修改成功！");
        }
        dictModelService.up(id);
        return Message.success("修改成功！");
    }
}
