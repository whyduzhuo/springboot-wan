package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Coding;

/**
 * @author: wanhy
 * @date: 2020/1/16 14:58
 */

public interface CodingDao extends BaseDao<Coding,Long> {
    Coding findByEntityPackages(String entityPackages);
}
