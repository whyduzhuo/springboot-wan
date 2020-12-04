package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:48
 */
@Transactional(rollbackFor = Exception.class)
public interface OrganizationDao extends BaseDao<Organization,Long> {
    /**
     *
     * @param pid
     * @return
     */
    @Query(value = "SELECT nvl(max(orders),0) FROM T_BASE_Organization WHERE PARENT_ID=?",nativeQuery = true)
    BigDecimal getMaxOrder(Long pid);
}
