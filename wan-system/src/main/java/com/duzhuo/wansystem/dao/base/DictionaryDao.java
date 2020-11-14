package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.DictModel;
import com.duzhuo.wansystem.entity.base.Dictionary;
import org.springframework.data.jpa.repository.Query;

/**
 * @author: wanhy
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
}
