package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.DictModelDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字典模块
 * @author: wanhy
 * @date: 2020/8/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictModelService extends BaseService<DictModel,Long> {
    public static final Pattern PATTERN = Pattern.compile("[A-Z][0-9]{3}");

    @Resource
    private DictModelDao dictModelDao;

    @Resource
    public void setBaseDao(DictModelDao dictModelDao){
        super.setBaseDao(dictModelDao);
    }

    public DictModel findByModelCode(String modelCode) {
        return dictModelDao.findByModelCode(modelCode);
    }

    /**
     * 字典模块--新增
     * @param dictModelVO
     * @return
     */
    public Message addData(DictModel dictModelVO){
        this.check(dictModelVO);
        super.save(dictModelVO);
        return Message.success("添加成功!");
    }

    /**
     * 字典模块--修改
     * @return
     */
    public Message edit(DictModel dictModelVO){
        this.check(dictModelVO);
        DictModel dictModel = super.find(dictModelVO.getId());
        dictModel.setModelCode(dictModelVO.getModelCode());
        dictModel.setModelName(dictModelVO.getModelName());
        super.update(dictModel);
        return Message.success("修改成功!");
    }

    /**
     * 字典模块--字段校验
     * @param dictModelVO
     */
    private void check(DictModel dictModelVO){
        if (StringUtils.isBlank(dictModelVO.getModelName())){
            throw new ServiceException("请输入模块名称！");
        }
        if (StringUtils.isBlank(dictModelVO.getModelCode())){
            throw new ServiceException("请输入模块编码！");
        }
        if (PATTERN.matcher(dictModelVO.getModelCode()).matches()){
            throw new ServiceException("模块编码不符合规范！规范：A001至Z999");
        }
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
            return a+(num+1)+"";
        }
        if (a=='Z'){
            throw new ServiceException("字典编码已耗尽！");
        }
        char b = (char)((int)(a)+1);
        return b+"001";
    }
}
