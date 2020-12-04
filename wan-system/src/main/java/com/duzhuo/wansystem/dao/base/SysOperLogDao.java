package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/4 11:31
 */
@Transactional(rollbackFor = Exception.class)
public interface SysOperLogDao extends BaseDao<SysOperLog,Long> {
}
