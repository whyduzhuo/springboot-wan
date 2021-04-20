package com.duzhuo.wansystem.dao.base;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/2 8:52
 */
@Transactional(rollbackFor = Exception.class)
public interface ProFileDao extends BaseDao<ProFile,Long> {

    /**
     * 查询是否有相同文件
     * @param fileSize
     * @param md5
     * @return
     */
    List<ProFile> findByFileSizeAndMd5AndStatus(Long fileSize, String md5, ProFile.Status status);
}
