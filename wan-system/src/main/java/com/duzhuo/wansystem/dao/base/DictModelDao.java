package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/10 11:17
 */
@Transactional(rollbackFor = Exception.class)
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

    /**
     * 获取最大的排序
     * @return
     */
    @Query(value = "SELECT nvl(max(orders),0) from T_BASE_DICTMODEL" ,nativeQuery = true)
    BigDecimal getMaxOrder();
}
