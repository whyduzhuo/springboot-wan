package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Menu;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:49
 */

public interface MenuDao extends BaseDao<Menu,Long>{
    @Query(value = "select max(id) from T_BASE_MENU where parent_id = ?",nativeQuery = true)
    BigDecimal getMaxBother(Long parentId);

    @Query(value = "select max(id) from T_BASE_MENU where parent_id is null",nativeQuery = true)
    BigDecimal getMaxTop();

    @Query(value = "INSERT INTO T_BASE_MENU (ID, CREATE_DATE, MODIFY_DATE, NAME, PATH, PARENT_ID, OS,TYPE,IS_ENABLE,ORDERS) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?)",nativeQuery = true)
    @Modifying
    int save(Long id, Date date, Date date1, String name, String path, Long parentId, int os, int type, int isEnable, Integer order);
}
