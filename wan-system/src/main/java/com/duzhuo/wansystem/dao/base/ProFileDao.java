package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:52
 */
@Transactional(rollbackFor = Exception.class)
public interface ProFileDao extends BaseDao<ProFile,Long> {
}
