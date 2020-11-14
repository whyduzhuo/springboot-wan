package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.CommonUtil;
import com.duzhuo.wansystem.entity.base.DictModel;
import com.duzhuo.wansystem.entity.base.Dictionary;
import com.duzhuo.wansystem.service.base.DictModelService;
import com.duzhuo.wansystem.service.base.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String list(HttpServletRequest request, CustomSearch<Dictionary> customSearch, Model model,Long modelId){
        CommonUtil.initPage(request,customSearch);
        Map<String,Object> searchParams = WebUtils.getParametersStartingWith(request,SEARCH_PREFIX);
        super.searchParamsTrim(searchParams);
        customSearch.getFilters().add(Filter.eq("dictModel.id",model));
        customSearch.getOrders().add(Sort.Order.asc("orders"));
        customSearch.setPagedata(dictionaryService.search(searchParams,customSearch));
        model.addAttribute("customSearch",customSearch);
        model.addAttribute("searchParams",mapKeyAddPre(searchParams, SEARCH_PREFIX));
        return "/base/dictionary/list";
    }

    @Log(title = "字典--编辑窗口",operateType = OperateType.SELECT)
    @ApiOperation(value = "字典--编辑窗口")
    @GetMapping("/editWin")
    public String editWin(Long modelId,Long id,Model model){
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
        return "/base/dictionary/editWin";
    }


    @ApiOperation(value = "字典--新增")
    @Log(title = "字典--新增",operateType = OperateType.INSERT)
    @PostMapping("/addData")
    @ResponseBody
    public Message addData(Dictionary dictionaryVO){
        return dictionaryService.addData(dictionaryVO);
    }

    @ApiOperation(value = "字典--修改")
    @Log(title = "字典--修改",operateType = OperateType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Message edit(Dictionary dictionaryVO){
        return dictionaryService.edit(dictionaryVO);
    }

    @ApiOperation(value = "字典--启用/禁用")
    @Log(title = "字典--启用/禁用",operateType = OperateType.UPDATE)
    @PostMapping("/onOff")
    @ResponseBody
    public Message onOff(Long id){
        Dictionary dictionary = dictionaryService.find(id);
        dictionary.setStatus(Dictionary.Status.values()[1-dictionary.getStatus().ordinal()]);
        return Message.success("修改成功!");
    }

    @ApiOperation(value = "字典--修改排序")
    @PostMapping("/upOrDown")
    @ResponseBody
    @Log(title = "字典--修改排序",operateType = OperateType.UPDATE)
    public Message upOrDown(Long id,Integer change){
        if (change>0){
            return dictionaryService.down(id);
        }
        return dictionaryService.up(id);
    }

}
