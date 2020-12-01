package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.OrderService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.StrFormatter;
import com.duzhuo.wansystem.dao.base.DictModelDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典模块
 * @author: 万宏远
 * @date: 2020/8/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictModelService extends OrderService<DictModel,Long> {

    @Resource
    private DictModelDao dictModelDao;

    @Resource
    public void setBaseDao(DictModelDao dictModelDao){
        super.setBaseDao(dictModelDao);
    }

    public DictModel findByModelCode(String modelCode) {
        return dictModelDao.findByModelCode(modelCode);
    }

    public DictModel findByModelName(String modelName) {
        return dictModelDao.findByModelName(modelName);
    }

    /**
     * 字典模块--新增
     * @param dictModelVO
     * @return
     */
    public void addData(DictModel dictModelVO){
        super.validation(dictModelVO);
        this.check(dictModelVO);
        super.save(dictModelVO);
    }

    /**
     * 字典模块--修改
     * @return
     */
    public void edit(DictModel dictModelVO){
        super.validation(dictModelVO);
        this.check(dictModelVO);
        DictModel dictModel = super.find(dictModelVO.getId());
        dictModel.setModelCode(dictModelVO.getModelCode());
        dictModel.setModelName(dictModelVO.getModelName());
        super.update(dictModel);
    }

    /**
     * 字典模块--字段校验
     * @param dictModelVO
     */
    private void check(DictModel dictModelVO){
        if (this.isExitName(dictModelVO)){
            throw new ServiceException("模块名称已存在！");
        }
        if (this.isExitCode(dictModelVO)){
            throw new ServiceException("模块编码已存在！");
        }
    }

    /**
     * 判断模块名是否存在
     * @param dictModelVO
     * @return
     */
    private boolean isExitName(DictModel dictModelVO){
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("modelName",dictModelVO.getModelName()));
        if (dictModelVO.getId()!=null){
            filters.add(Filter.ne("id", dictModelVO.getId()));
        }
        return super.count(filters)>0;
    }

    /**
     * 判断模块编码是否存在
     * @param dictModelVO
     * @return
     */
    private boolean isExitCode(DictModel dictModelVO){
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("modelCode",dictModelVO.getModelCode()));
        if (dictModelVO.getId()!=null){
            filters.add(Filter.ne("id", dictModelVO.getId()));
        }
        return super.count(filters)>0;
    }

    /**
     * 字典编码
     * 如：A001
     * @return
     */
    public String getNewCode() {
        String maxCode = dictModelDao.getMaxCode();
        if (StringUtils.isBlank(maxCode)){
            return "A001";
        }
        char a = maxCode.charAt(0);
        int num = Integer.valueOf(maxCode.substring(1,maxCode.length()));
        if (num<999){
            return String.valueOf(a)+ StrFormatter.formatNumber(num+1,3);
        }
        if (a=='Z'){
            throw new ServiceException("字典编码已耗尽！");
        }
        char b = (char)((int)(a)+1);
        return b+"001";
    }

    public Integer getNewOrder(){
        return dictModelDao.getMaxOrder().intValue()+1;
    }

    /**
     * 获取所有的字典并排序
     * @return
     */
    @Override
    public List<DictModel> findAll(){
        return super.searchList(null,Sort.by(Sort.Direction.ASC,"order"));
    }


}
