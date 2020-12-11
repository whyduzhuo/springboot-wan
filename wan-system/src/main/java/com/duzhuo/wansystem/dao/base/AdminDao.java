package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.wansystem.entity.base.Admin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:52
 */
@Transactional(rollbackFor = Exception.class)
public interface AdminDao extends BaseDao<Admin,Long> {
    /**
     * 查询用户
     * @param username
     * @param password
     * @return
     */
    Admin findByUsernameAndPassword(String username, String password);

    /**
     *
     * @return
     */
    Admin findByUsernameAndIsDelete(String username,IsDelete isDelete);

    /**
     *
     * @return
     */
    Admin findByUsername(String username);

    /**
     *
     * @param adminId
     * @return
     */
    @Query(value = "select ROLE_ID from T_BASE_ADMIN_ROLE where ADMIN_ID = ?1 AND FLAG=1",nativeQuery = true)
    Long getCurrRoleId(Long adminId);

    @Query(value = "UPDATE T_BASE_ADMIN_ROLE SET FLAG=1 WHERE ADMIN_ID=?1 AND ROLE_ID=?2",nativeQuery = true)
    @Modifying
    void addDefaultRole(Long adminId, Long roleId);
}
