package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.DictModel;
import com.duzhuo.wansystem.entity.base.Dictionary;
import com.duzhuo.wansystem.service.base.DictModelService;
import com.duzhuo.wansystem.service.base.DictionaryService;
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
 * @author: 万宏远
 * @date: 2020/8/11 14:01
 */
@RequestMapping("/base/dictionary")
@Controller
@Api(value = "字典")
public class DictionaryController extends BaseController{

    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private DictModelService dictModelService;

    /**
     *
     * @param request
     * @param customSearch
     * @param model
     * @param modelId
     * @return
     */
    @Log(title = "字典--列表页",operateType = OperateType.SELECT)
    @ApiOperation(value = "字典--列表页")
    @GetMapping("/list")
    public String list(HttpServletRequest request, CustomSearch<Dictionary> customSearch, Model model, Long modelId){
        if (modelId==null){
            throw new ServiceException("请选择字典模块");
        }
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        customSearch.getFilters().add(Filter.eq("dictModel.id",modelId));
        customSearch.getOrders().add(Sort.Order.asc("status"));
        customSearch.getOrders().add(Sort.Order.asc("order"));
        customSearch.setPagedata(dictionaryService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        model.addAttribute("dictModel",dictModelService.find(modelId));
        return "/base/dictionaryModel/dictionary/list";
    }

    @RequiresPermissions({"100502","100503"})
    @Log(title = "字典--编辑窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "字典--编辑窗口")
    @GetMapping("/detail")
    public String detail(Long modelId,Long id,Model model){
        Dictionary dictionary;
        DictModel dictModel;
        if (id!=null){
            dictionary = dictionaryService.find(id);
        }else{
            dictionary = new Dictionary();
            dictModel = dictModelService.find(modelId);
            dictionary.setDictModel(dictModel);
            dictionary.setOrder(dictionaryService.getMaxOrder(dictModel)+1);
            dictionary.setCode(dictionaryService.getNewCode(dictModel));
        }
        model.addAttribute("data",dictionary);
        model.addAttribute("statusList",Dictionary.Status.values());
        return "/base/dictionaryModel/dictionary/edit";
    }

    @RequiresPermissions("100502")
    @ApiOperation(value = "字典--新增")
    @Log(title = "字典--新增",operateType = OperateType.INSERT)
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Dictionary dictionaryVO){
        dictionaryService.addData(dictionaryVO);
        return Message.success("添加成功!");
    }

    @RequiresPermissions("100503")
    @ApiOperation(value = "字典--修改")
    @Log(title = "字典--修改",operateType = OperateType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(Dictionary dictionaryVO){
        dictionaryService.edit(dictionaryVO);
        return Message.success("修改成功！");
    }

    @RequiresPermissions("100503")
    @ApiOperation(value = "字典--启用/禁用")
    @Log(title = "字典--启用/禁用",operateType = OperateType.UPDATE)
    @PostMapping("/onOff")
    @ResponseBody
    public Message onOff(Long id){
        Dictionary dictionary = dictionaryService.find(id);
        dictionary.setStatus(Dictionary.Status.values()[1-dictionary.getStatus().ordinal()]);
        return Message.success("修改成功!");
    }

    @RequiresPermissions("100503")
    @ApiOperation(value = "字典--修改排序")
    @PostMapping("/upOrDown")
    @ResponseBody
    @Log(title = "字典--修改排序",operateType = OperateType.UPDATE)
    public Message upOrDown(Long id,Integer change){
        if (change<0){
            dictionaryService.down(id);
            return Message.success("修改成功！");
        }
        dictionaryService.up(id);
        return Message.success("修改成功！");
    }

}
