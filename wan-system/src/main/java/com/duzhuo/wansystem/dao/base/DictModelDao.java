package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.DictModel;

/**
 * @author: wanhy
 * @date: 2020/8/10 11:17
 */

public interface DictModelDao extends BaseDao<DictModel,Long> {

    DictModel findByModelCode(String modelCode);
}
