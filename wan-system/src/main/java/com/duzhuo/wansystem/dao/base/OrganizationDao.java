package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Organization;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:48
 */
@Transactional(rollbackFor = Exception.class)
public interface OrganizationDao extends BaseDao<Organization,Long> {
}
