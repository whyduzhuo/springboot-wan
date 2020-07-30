package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Organization;
import com.duzhuo.wansystem.entity.base.Position;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:49
 */

public interface PositionDao extends BaseDao<Position,Long>{
    /**
     * name/organization 联合唯一
     * @param name
     * @param organization
     * @return
     */
    int countByNameAndOrganization(String name,Organization organization);
}
