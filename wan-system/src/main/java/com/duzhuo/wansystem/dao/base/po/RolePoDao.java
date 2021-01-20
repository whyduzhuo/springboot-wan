package com.duzhuo.wansystem.dao.base.po;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.po.RolePo;
import org.springframework.transaction.annotation.Transactional;


/**
 * 角色管理--Mapper
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/01/20 09:37:13
 */
@Transactional(rollbackFor = Exception.class)
public interface RolePoDao extends BaseDao<RolePo,Long> {
}

