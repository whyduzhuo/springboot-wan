package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import com.duzhuo.wansystem.dao.base.CodingDao;
import com.duzhuo.wansystem.entity.base.Coding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: 万宏远
 * @date: 2020/1/13 15:36
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CodingService extends BaseService<Coding,Long>{
    @Resource
    private CodingDao codingDao;
    @Resource
    public void setBaseDao(CodingDao codingDao){
        super.setBaseDao(codingDao);
    }

    /**
     * @param codingVO
     * @return
     */
    public Message addData(Coding codingVO){
        super.validation(codingVO);
        if (this.findByEntityPackages(codingVO.getEntityPackages())!=null){
            throw new ServiceException("类已存在！");
        }
        super.save(codingVO);
        return Message.success("添加成功！");
    }

    /**
     *
     * @return
     */
    public Message edit(Coding codingVO){
        super.update(codingVO);
        return Message.success("修改成功！");
    }

    private Coding findByEntityPackages(String entityPackages) {
        return codingDao.findByEntityPackages(entityPackages);
    }
}
