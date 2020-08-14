package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:49
 */

public interface MenuDao extends BaseDao<Menu,Long>{
    @Query(value = "select max(num) from T_BASE_MENU where parent_id = ?",nativeQuery = true)
    BigDecimal getMaxBother(Long parentId);

    @Query(value = "select max(num) from T_BASE_MENU where parent_id is null",nativeQuery = true)
    BigDecimal getMaxTop();

    @Query(value = "DELETE FROM T_BASE_ROLE_MENU WHERE MENU_ID=?",nativeQuery = true)
    @Modifying
    void delMenuRole(Long menuId);

    /**
     *
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
     *
     * @param parentId
     * @return
     */
    @Query(value = "SELECT nvl(MAX(ORDERS),0) FROM T_BASE_MENU WHERE PARENT_ID=?",nativeQuery = true)
    BigDecimal getMaxOrder(Long parentId);
}
