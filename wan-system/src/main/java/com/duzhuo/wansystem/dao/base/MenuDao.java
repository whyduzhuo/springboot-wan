package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:49
 */

public interface MenuDao extends BaseDao<Menu,Long>{
    /**
     * 查询最大儿子编号
     * @param parentId
     * @return
     */
    @Query(value = "select max(num) from T_BASE_MENU where parent_id = ?",nativeQuery = true)
    BigDecimal getMaxBother(Long parentId);

    /**
     * 查询根节点最大编号
     * @return
     */
    @Query(value = "select max(num) from T_BASE_MENU where parent_id is null",nativeQuery = true)
    BigDecimal getMaxTop();

    /**
     * 删除某个菜单的，角色菜单关联表
     * @param menuId
     */
    @Query(value = "DELETE FROM T_BASE_ROLE_MENU WHERE MENU_ID=?",nativeQuery = true)
    @Modifying
    void delMenuRole(Long menuId);

    /**
     * 查询菜单的子节点个数
     * @param parendId
     * @return
     */
    @Query(value = "SELECT count(*) FROM T_BASE_MENU WHERE PARENT_ID=?",nativeQuery = true)
    BigDecimal haveChriden(Long parendId);

    /**
     * 查询根节点
     * @return
     */
    List<Menu> findByParentIsNullOrderByOrder();

    /**
     * 查询排序最大的子节点
     * @param parentId
     * @return
     */
    @Query(value = "SELECT nvl(MAX(ORDERS),0) FROM T_BASE_MENU WHERE PARENT_ID=?",nativeQuery = true)
    BigDecimal getMaxOrder(Long parentId);

    /**
     * 给某角色赋予菜单权限
     * @param roleId
     * @param menuId
     */
    @Query(value = "INSERT INTO T_BASE_ROLE_MENU (ROLE_ID, MENU_ID) VALUES (?1,?2)",nativeQuery = true)
    @Modifying
    void grantMenu(Long roleId, Long menuId);

    /**
     * 判断某角色是否有某个菜单
     * @param roleId
     * @param menuId
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM T_BASE_ROLE_MENU WHERE ROLE_ID = ?1 AND MENU_ID=?2",nativeQuery = true)
    BigDecimal hasMenu(Long roleId, Long menuId);

    /**
     * 移除某角色的菜单
     * @param roleId
     * @param menuId
     */
    @Query(value = "DELETE FROM T_BASE_ROLE_MENU WHERE  ROLE_ID =?1 AND MENU_ID = ?2",nativeQuery = true)
    @Modifying
    void delMenu(Long roleId, Long menuId);

    /**
     * 删除某角色下的全部菜单
     * @param roleId
     */
    @Query(value = "DELETE FROM T_BASE_ROLE_MENU WHERE ROLE_ID = ?",nativeQuery = true)
    @Modifying
    void delAllMenu(Long roleId);

    /**
     * 查询所有的父节点，包括自己
     * @param menuId
     * @return
     */
    @Query(value = "SELECT * FROM T_BASE_MENU START WITH ID = 68187 CONNECT BY PRIOR PARENT_ID = ID order by num asc",nativeQuery = true)
    List<Menu> getAllParent(Long menuId);
}
