package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.base.Admin;
import org.springframework.stereotype.Repository;

/**
 * @author: wanhy
 * @date: 2020/1/2 8:52
 */
public interface AdminDao extends BaseDao<Admin,Long> {
    Admin findByUsernameAndPassword(String username, String password);
}
