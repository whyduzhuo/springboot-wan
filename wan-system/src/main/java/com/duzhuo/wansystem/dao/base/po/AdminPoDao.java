package com.duzhuo.wansystem.dao.base.po;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.po.AdminPo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:52
 */
@Transactional(rollbackFor = Exception.class)
public interface AdminPoDao extends BaseDao<AdminPo,Long> {

}
