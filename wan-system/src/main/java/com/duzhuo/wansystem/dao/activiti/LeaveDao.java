package com.duzhuo.wansystem.dao.activiti;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.activiti.Leave;
import org.springframework.transaction.annotation.Transactional;


/**
 * 请假申请--Mapper
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/03/11 16:28:18
 */
@Transactional(rollbackFor = Exception.class)
public interface LeaveDao extends BaseDao<Leave,Long> {
}

