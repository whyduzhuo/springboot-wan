package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.Organization;
import com.duzhuo.wansystem.entity.base.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:49
 */
@Transactional(rollbackFor = Exception.class)
public interface RoleDao extends BaseDao<Role,Long> {
    /**
     * name/organization 联合唯一
     * @param name
     * @param organization
     * @return
     */
    int countByNameAndOrganization(String name,Organization organization);

    /**
     * 判断某人是否有某角色
     * @param roleId
     * @param adminId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_ADMIN_ROLE WHERE ROLE_ID=? AND ADMIN_ID =?",nativeQuery = true)
    BigDecimal hasRole(Long roleId, Long adminId);

    /**
     * 删除某人的某个角色
     * @param roleId
     * @param adminId
     */
    @Query(value = "DELETE FROM T_BASE_ADMIN_ROLE WHERE ROLE_ID=? AND ADMIN_ID=?",nativeQuery = true)
    @Modifying
    void delRole(Long roleId, Long adminId);

    /**
     * 给某人授予某角色
     * @param roleId
     * @param adminId
     * @return
     */
    @Query(value = "INSERT INTO T_BASE_ADMIN_ROLE (ROLE_ID,ADMIN_ID) VALUES (?,?)",nativeQuery = true)
    @Modifying
    void addRole(Long roleId, Long adminId);

    /**
     * 查询有某个角色的人数
     * @param roleId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_ADMIN_ROLE WHERE ROLE_ID = ?",nativeQuery = true)
    BigDecimal countByRoleNumber(Long roleId);

    /**
     * 查询某角色的菜单总数
     * @param roleId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_ROLE_MENU WHERE ROLE_ID=?",nativeQuery = true)
    BigDecimal countByMenu(Long roleId);

    /**
     * 查询拥有某个菜单的全部角色
     * @param menuId
     * @return
     */
    @Query(value = "SELECT t1.* from T_BASE_ROLE t1 " +
            "LEFT JOIN T_BASE_ROLE_MENU t2 ON t1.id = t2.role_id " +
            "WHERE t2.menu_id = ? and t1.id is not null ORDER BY t1.orders ASC "
            ,nativeQuery = true)
    List<Role> getAllRoles(Long menuId);

    /**
     * 查询某个用户的全部角色
     * @param adminId
     * @return
     */
    @Query(value = "SELECT t1.* FROM T_BASE_ROLE t1 " +
            "LEFT JOIN T_BASE_ADMIN_ROLE t2 ON t1.id = t2.role_id " +
            "WHERE t2.admin_id = ?1 ORDER BY t1.orders asc",nativeQuery = true)
    List<Role> getAllRolesByAdmin(Long adminId);
}
