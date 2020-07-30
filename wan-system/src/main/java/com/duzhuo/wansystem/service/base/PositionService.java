package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.PositionDao;
import com.duzhuo.wansystem.entity.base.Position;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:50
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PositionService extends BaseService<Position,Long> {
    @Resource
    private PositionDao positionDao;

    @Resource
    public void setBaseDao(PositionDao positionDao){
        super.setBaseDao(positionDao);
    }

    /**
     * 新增菜单
     * @param positionVo
     * @return
     */
    public Message insert(Position positionVo) {
        if (positionVo.getType()==null){
            throw new ServiceException("");
        }
        if (StringUtils.isNotBlank(positionVo.getName())){
            throw new ServiceException("请输入名称");
        }
        if (positionVo.getOrganization()==null || positionVo.getOrganization().getId()==null){
            throw new ServiceException("请选择部门");
        }
        int c = positionDao.countByNameAndOrganization(positionVo.getName(),positionVo.getOrganization());
        if (c>0){
            throw new ServiceException("已存在！");
        }
        super.save(positionVo);
        return Message.success("添加成功!");
    }

    /**
     * 修改菜单
     * @param positionVo
     * @return
     */
    public Message edit(Position positionVo) {
        if (StringUtils.isNotBlank(positionVo.getName())){
            throw new ServiceException("请输入名称");
        }
        if (positionVo.getOrganization()==null || positionVo.getOrganization().getId()==null){
            throw new ServiceException("请选择部门");
        }
        if (this.exist(positionVo)){
            throw new ServiceException("已存在！");
        }
        Position position = super.find(positionVo.getId());
        if (position.getType()==Position.Type.固定职务){
            throw new ServiceException("固定职务/角色，不可修改！");
        }
        position.setName(positionVo.getName()).setOrganization(positionVo.getOrganization());
        super.update(position);
        return Message.success("修改成功！！");
    }

    /**
     * 查询职务是否存在
     * @param positionVo
     * @return
     */
    private Boolean exist(Position positionVo){
        List<Filter> filters = new ArrayList<>();
        filters.add(Filter.eq("name",positionVo.getName()));
        filters.add(Filter.eq("organization.id",positionVo.getOrganization().getId()));
        filters.add(Filter.ne("id",positionVo.getId()));
        return super.count(filters)>0;
    }


    /**
     * 用户绑定角色
     * @param roleId
     * @param adminId
     * @return
     */
    public Message addRole(Long roleId,Long adminId){
        if (this.hasRole(roleId,adminId)!=null){
            return Message.success();
        }
        positionDao.addRole(roleId,adminId);
        return Message.success();
    }

    /**
     * 用户解绑角色
     * @param roleId
     * @param adminId
     * @return
     */
    public Message delRole(Long roleId,Long adminId){
        positionDao.delRole(roleId,adminId);
        return Message.success("删除成功！");
    }

    /**
     * 查询是否有某角色
     * @param roleId
     * @param adminId
     * @return
     */
    public Boolean hasRole(Long roleId,Long adminId){
        return positionDao.hasRole(roleId,adminId);
    }

    /**
     * 删除角色/职务
     */
    @Override
    public void delete(Long id){
        if (this.countByRoleNumber(id)>0){
            throw new ServiceException("请先移除所有人！");
        }
        if (this.countByMenu(id)>0){
            throw new ServiceException("请先移除所有菜单！");
        }
        super.delete(id);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void delete(Long... ids){
        Arrays.stream(ids).forEach(this::delete);
    }

    /**
     * 查询有某个角色的人数
     * @param roleId
     * @return
     */
    public int countByRoleNumber(Long roleId){
        return positionDao.countByRoleNumber(roleId).intValue();
    }

    /**
     * 查询某角色的菜单总数
     * @param roleId
     * @return
     */
    public int countByMenu(Long roleId){
        return positionDao.countByMenu(roleId).intValue();
    }
}
