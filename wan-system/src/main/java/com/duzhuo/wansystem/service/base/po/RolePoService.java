package com.duzhuo.wansystem.service.base.po;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.wansystem.dao.base.po.RolePoDao;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.entity.base.po.RolePo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 角色管理--Service
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/01/20 09:37:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RolePoService extends BaseService<RolePo, Long> {

    @Resource
    private RolePoDao rolePoDao;

    @Resource
    public void setBaseDao(RolePoDao rolePoDao){
        super.setBaseDao(rolePoDao);
    }

    /**
     *
     * @return
     */
    public RolePo getRolePo(Role role){
        return super.find(role.getId());
    }
}
