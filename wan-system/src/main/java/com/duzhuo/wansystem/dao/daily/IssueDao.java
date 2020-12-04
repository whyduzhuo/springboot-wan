package com.duzhuo.wansystem.dao.daily;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.daily.Issue;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/5/19 18:04
 */
@Transactional(rollbackFor = Exception.class)
public interface IssueDao extends BaseDao<Issue,Long> {
}
