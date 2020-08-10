package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.wansystem.dao.base.DictionaryDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import com.duzhuo.wansystem.entity.base.Dictionary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/8/10 11:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryService extends BaseService<Dictionary,Long> {

    @Resource
    private DictModelService dictModelService;
    @Resource
    private DictionaryDao dictionaryDao;

    @Resource
    public void setBaseDao(DictionaryDao dictionaryDao){
        super.setBaseDao(dictionaryDao);
    }

    /**
     * 查询某个模块的字典
     * @param dictModel
     * @return
     */
    public List<Dictionary> getList(DictModel dictModel){
        if (dictModel.getDictionaryList().isEmpty()){
            dictModel  = dictModelService.find(dictModel.getId());
        }
        return dictModel.getDictionaryList(Dictionary.Status.启用);
    }

    /**
     * 查询某个模块的字典
     * @param modelId
     * @return
     */
    public List<Dictionary> getList(Long modelId){
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("status",Dictionary.Status.启用));
        filters.add(Filter.eq("dictModel.id",modelId));
        return super.searchList(filters, Sort.by(Sort.Direction.ASC,"code"));
    }

    /**
     * 查询某个模块的字典
     * @param modelCode
     * @return
     */
    public List<Dictionary> getList(String modelCode){
        DictModel dictModel = dictModelService.findByModelCode(modelCode);
        return dictModel.getDictionaryList(Dictionary.Status.启用);
    }
}
