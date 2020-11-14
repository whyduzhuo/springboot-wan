package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: wanhy
 * @date: 2020/8/10 11:17
 */

public interface DictModelDao extends BaseDao<DictModel,Long> {

    /**
     *
     * @param modelCode
     * @return
     */
    DictModel findByModelCode(String modelCode);

    /**
     * 获取最大的code
     * @return
     */
    @Query(value = "select max(model_Code) from T_BASE_DICTMODEL",nativeQuery = true)
    String getMaxCode();

    DictModel findByModelName(String modelName);
}
