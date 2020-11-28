package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Coding;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/16 14:58
 */

public interface CodingDao extends BaseDao<Coding,Long> {
    /**
     * 根据类名查询
     * @param entityPackages
     * @return
     */
    Coding findByEntityPackages(String entityPackages);
}
