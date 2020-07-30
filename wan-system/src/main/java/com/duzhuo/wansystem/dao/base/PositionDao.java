package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.common.core.Message;
import com.duzhuo.wansystem.entity.base.Organization;
import com.duzhuo.wansystem.entity.base.Position;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     *
     * @param roleId
     * @param adminId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_ADMIN_POSITION WHERE POSITION_ID=? AND ADMIN_ID =?",nativeQuery = true)
    Boolean hasRole(Long roleId, Long adminId);

    /**
     *
     * @param roleId
     * @param adminId
     */
    @Query(value = "DELETE FROM T_BASE_ADMIN_POSITION WHERE POSITION_ID=? AND ADMIN_ID=?",nativeQuery = true)
    @Modifying
    void delRole(Long roleId, Long adminId);

    /**
     *
     * @param roleId
     * @param adminId
     * @return
     */
    @Query(value = "INSERT INTO T_BASE_ADMIN_POSITION (POSITION_ID,ADMIN_ID) VALUES (?,?)",nativeQuery = true)
    @Modifying
    BigDecimal addRole(Long roleId, Long adminId);

    /**
     *
     * @param roleId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_ADMIN_POSITION WHERE POSITION_ID = ?",nativeQuery = true)
    BigDecimal countByRoleNumber(Long roleId);

    /**
     *
     * @param roleId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_POSITION_MENU WHERE POSITION_ID=?",nativeQuery = true)
    BigDecimal countByMenu(Long roleId);
}
