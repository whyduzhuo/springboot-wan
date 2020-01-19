package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.Tools;
import com.duzhuo.wansystem.dao.base.CodingDao;
import com.duzhuo.wansystem.entity.base.Coding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: wanhy
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
        if (!Tools.vaildeParam(codingVO.getEntityPackages())){
            throw new ServiceException("完整类名不可为空！");
        }
        if (!Tools.vaildeParam(codingVO.getAuthor())){
            throw new ServiceException("作者不可为空！");
        }
        if (!Tools.vaildeParam(codingVO.getDaopackage())){
            throw new ServiceException("DAO包名为空！");
        }
        if (!Tools.vaildeParam(codingVO.getControllerpackage())){
            throw new ServiceException("Controller包名为空！");
        }
        if (!Tools.vaildeParam(codingVO.getServicepackage())){
            throw new ServiceException("Service包名为空！");
        }
        if (this.findByEntityPackages(codingVO.getEntityPackages())!=null){
            throw new ServiceException("类已存在！");
        }
        super.save(codingVO);
        return Message.success("添加成功！");
    }

    private Coding findByEntityPackages(String entityPackages) {
        return codingDao.findByEntityPackages(entityPackages);
    }
}
