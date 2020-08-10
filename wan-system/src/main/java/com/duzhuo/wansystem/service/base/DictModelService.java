package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.wansystem.dao.base.DictModelDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/8/10 11:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictModelService extends BaseService<DictModel,Long> {
    @Resource
    private DictModelDao dictModelDao;

    @Resource
    public void setBaseDao(DictModelDao dictModelDao){
        super.setBaseDao(dictModelDao);
    }

    public DictModel findByModelCode(String modelCode) {
        return dictModelDao.findByModelCode(modelCode);
    }
}
