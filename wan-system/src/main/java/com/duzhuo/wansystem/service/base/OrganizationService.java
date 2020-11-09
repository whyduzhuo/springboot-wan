package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.OrganizationDao;
import com.duzhuo.wansystem.entity.base.Organization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationService extends BaseService<Organization,Long> {
    @Resource
    private OrganizationDao organizationDao;

    @Resource
    public void setBaseDao(OrganizationDao organizationDao){
        super.setBaseDao(organizationDao);
    }

    /**
     * 部门/机构 -- 新增
     * @param organizationVO
     * @return
     */
    public Message addData(Organization organizationVO) {
        super.validation(organizationVO);
        this.check(organizationVO);
        super.save(organizationVO);
        return Message.success("添加成功!");
    }

    /**
     * 部门/机构 -- 修改
     * @param organizationVO
     * @return
     */
    public Message edit(Organization organizationVO) {
        super.validation(organizationVO);
        this.check(organizationVO);
        return Message.error("功能暂未完成！");
    }

    private void check(Organization organizationVO){
        throw new ServiceException("功能暂未完成！");
    }
}
