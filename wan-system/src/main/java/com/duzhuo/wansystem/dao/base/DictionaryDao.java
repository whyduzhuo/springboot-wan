package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import com.duzhuo.wansystem.entity.base.Dictionary;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/10 11:16
 */

public interface DictionaryDao extends BaseDao<Dictionary,Long> {
    /**
     *
     * @param modelId
     * @return
     */
    @Query(value = "select max(code) from T_BASE_DICTIONARY",nativeQuery = true)
    String getMaxCode(Long modelId);

    Dictionary findByDictModelAndCode(DictModel dictModel, String code);

    Dictionary findByDictModelAndValue(DictModel dictModel, String value);

    @Query(value = "SELECT * FROM(SELECT * FROM T_BASE_DICTIONARY WHERE ORDERS < ? ORDER BY ORDERS DESC) WHERE ROWNUM =1",nativeQuery = true)
    Dictionary getUper(Integer order);

    @Query(value = "SELECT * FROM(SELECT * FROM T_BASE_DICTIONARY WHERE ORDERS > ? ORDER BY ORDERS ASC) WHERE ROWNUM =1",nativeQuery = true)
    Dictionary getDowner(Integer o);

    /**
     * 查询某个模块字典的最大排序
     * @param id
     * @return
     */
    @Query(value = "SELECT nvl(max(ORDERS),0) FROM T_BASE_DICTIONARY WHERE MODEL_ID=?",nativeQuery = true)
    BigDecimal getMaxOrder(Long id);
}
