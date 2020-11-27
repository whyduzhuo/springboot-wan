package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.wansystem.entity.base.Admin;

/**
 * @author: wanhy
 * @date: 2020/1/2 8:52
 */
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
}
