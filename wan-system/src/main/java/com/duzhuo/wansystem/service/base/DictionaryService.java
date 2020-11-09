package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.StringUtils;
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
 * 字典
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
     * 字典--新增
     * @param dictionaryVO
     * @return
     */
    public Message addData(Dictionary dictionaryVO){
        super.validation(dictionaryVO);
        this.check(dictionaryVO);
        super.save(dictionaryVO);
        return Message.success("添加成功!");
    }

    /**
     * 字典--修改
     * @param dictionaryVO
     * @return
     */
    public Message edit(Dictionary dictionaryVO){
        super.validation(dictionaryVO);
        this.check(dictionaryVO);
        Dictionary dictionary = super.find(dictionaryVO.getId());
        dictionary.setCode(dictionaryVO.getCode());
        dictionary.setStatus(dictionaryVO.getStatus());
        dictionary.setValue(dictionaryVO.getValue());
        dictionary.setRemark(dictionaryVO.getRemark());
        super.update(dictionary);
        return Message.success("修改成功！");
    }


    /**
     * 判断是否重复
     * @param dictionaryVO
     * @return
     */
    private void check(Dictionary dictionaryVO){
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("code",dictionaryVO.getCode()));
        filters.add(Filter.eq("dictModel.id",dictionaryVO.getDictModel().getId()));
        if (dictionaryVO.getId()!=null){
            filters.add(Filter.ne("id",dictionaryVO.getId()));
        }
        if (super.count(filters)>0){
            throw new ServiceException("字典编码已存在！");
        }
        filters.remove(0);
        filters.add(Filter.eq("value",dictionaryVO.getValue()));
        if (super.count(filters)>0){
            throw new ServiceException("字典值已存在");
        }
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

    /**
     * 自动获取新code
     * @param modelId
     * @return
     */
    public String getNewCode(Long modelId){
        String maxCode = dictionaryDao.getMaxCode(modelId);
        if (StringUtils.isBlank(maxCode)){
            return "01";
        }
        String newCode;
        try {
            int i = Integer.valueOf(maxCode);
            newCode = (i+1)+"";
        }catch (NumberFormatException e){
            char[] chars = maxCode.toCharArray();
            char c = chars[chars.length-1];
            int cAsci = (int)(c);
            char b = (char)(cAsci+1);
            chars[chars.length-1]=b;
            newCode = String.valueOf(chars);
        }
        return newCode;
    }
}
