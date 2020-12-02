package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.DeleteService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.OrganizationDao;
import com.duzhuo.wansystem.entity.base.Organization;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationService extends DeleteService<Organization,Long> {
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

        return Message.error("功能暂未完成！");
    }

    private void check(Organization organizationVO){
        throw new ServiceException("功能暂未完成！");
    }

    /**
     * 获取全部可用部门
     * @return
     */
    public List<Organization> getAllEnable() {
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("delTime",0));
        return super.searchList(filters,Sort.by(Sort.Order.asc("order")));
    }
}
