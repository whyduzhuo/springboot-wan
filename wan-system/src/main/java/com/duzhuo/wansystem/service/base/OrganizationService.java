package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.delorder.DelOrderService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.OrganizationDao;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Organization;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationService extends DelOrderService<Organization,Long> {
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
    public void addData(Organization organizationVO) {
        super.validation(organizationVO);
        super.save(organizationVO);
    }

    /**
     * 部门/机构 -- 修改
     * @param organizationVO
     * @return
     */
    public void edit(Organization organizationVO) {
        super.validation(organizationVO);
        Organization organization = super.find(organizationVO.getId());
        organization.setOrder(organizationVO.getOrder());
        organization.setName(organizationVO.getName());
        super.save(organization);
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
        return super.searchList(filters,Sort.by(Sort.Order.asc("parent.order"),Sort.Order.asc("order")));
    }

    /**
     * 获取全部可用部门
     * @return
     */
    public List<Organization> getAllEnable(Long pid) {
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("parent.id",pid));
        filters.add(Filter.eq("delTime",0));
        return super.searchList(filters,Sort.by(Sort.Order.asc("order")));
    }

    /**
     * 获取全部可用部门,转树结构（已组装好）
     * @return
     */
    public List<Ztree> getAllEnableTree(){
        return Ztree.buildTree(toTree(getAllEnable()));
    }

    /**
     * 将organizationList转ztree对象
     * @param organizations
     * @return
     */
    public List<Ztree> toTree(Collection<Organization> organizations) {
        List<Ztree> ztreeList = new ArrayList<>();
        List<Organization> organizationList = new ArrayList<>(organizations);
        Collections.sort(organizationList);
        organizationList.forEach(m->ztreeList.add(organizationToTree(m)));
        return ztreeList;
    }

    /**
     * 将组织机构转tree对象
     * 不可勾选
     * @param organization
     * @return
     */
    public Ztree organizationToTree(Organization organization){
        Ztree ztree = new Ztree();
        ztree.setId(organization.getId());
        ztree.setName(organization.getName());
        ztree.setPid(organization.getParent()==null?null:organization.getParent().getId());
        ztree.setOpen(true);
        ztree.setOrders(organization.getOrder());
        return ztree;
    }

    /**
     *
     * @param pid
     * @return
     */
    public Integer getMaxOrder(Long pid) {
        return organizationDao.getMaxOrder(pid).intValue();
    }
}
