package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
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

    public Message insert(Organization organization) {
        return Message.error("功能暂未完成！");
    }

    public Message edit(Organization organization) {
        return Message.error("功能暂未完成！");
    }
}
