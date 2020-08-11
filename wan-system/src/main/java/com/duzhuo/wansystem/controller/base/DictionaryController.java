package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.BaseController;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Dictionary;
import com.duzhuo.wansystem.service.base.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/8/11 14:01
 */
@RequestMapping("/base/dictionary")
@Controller
@Api(value = "字典")
public class DictionaryController extends BaseController{

    @Resource
    private DictionaryService dictionaryService;

    @ApiOperation(value = "字典--新增/修改")
    @Log(title = "字典--新增/修改",operateType = OperateType.INSERT)
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Message saveOrUpdate(Dictionary dictionaryVO,boolean autoCode){
        if (dictionaryVO.getId()==null){
            if (autoCode){
                dictionaryVO.setCode(dictionaryService.getNewCode(dictionaryVO.getDictModel().getId()));
            }
            return dictionaryService.addData(dictionaryVO);
        }
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


}
