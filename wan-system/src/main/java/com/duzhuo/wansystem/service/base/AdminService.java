package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import com.duzhuo.wansystem.dao.base.AdminDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.shiro.AdminRealm;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/2 8:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminService extends BaseService<Admin,Long> {
    @Resource
    private AdminDao adminDao;
    @Resource
    private RoleService roleService;

    @Resource
    public void setBaseDao(AdminDao adminDao){
        super.setBaseDao(adminDao);
    }

    /**
     * 新增用户
     * @param adminVO
     * @return
     */
    public Message addData(Admin adminVO) {
        this.check(adminVO);
        super.save(adminVO);
        return Message.success("添加成功！");
    }

    /**
     * 修改用户
     * @param adminVO
     * @return
     */
    public Message edit(Admin adminVO){
//        this.check(adminVO);
//        super.update(adminVO);
        return Message.success("修改成功！");
    }


    /**
     * 数据校验
     * @param adminVO
     */
    public void check(Admin adminVO){
        if (StringUtils.isBlank(adminVO.getUsername())){
            throw new ServiceException("请输入用户名");
        }
        if (StringUtils.isBlank(adminVO.getRealname())){
            throw new ServiceException("请输入昵称");
        }
        if (StringUtils.isBlank(adminVO.getPassword())){
            throw new ServiceException("请输入密码");
        }
        List<Filter> filterList = new ArrayList<>();
        filterList.add(Filter.eq("username",adminVO.getUsername()));
        if (adminVO.getId()!=null){
            filterList.add(Filter.ne("username",adminVO.getUsername()));
        }
        if (super.count(filterList)>0){
            throw new ServiceException("用户名："+adminVO.getUsername()+"已存在！");
        }
    }

    public Admin getCurrent(){
        return ShiroUtils.getCurrAdmin();
    }

    public Admin findByUsernameAndPassword(String username, String password) {
        return adminDao.findByUsernameAndPassword(username,password);
    }

    public Admin login(String username, String password) throws Exception {
        if (StringUtils.isBlank(username)){
            throw new Exception("用户名为空！");
        }
        if (StringUtils.isBlank(password)){
            throw new Exception("密码为空！");
        }
        Admin admin = this.findByUsernameAndPassword(username,password);
        if (admin==null){
            throw new Exception("用户名或密码错误！");
        }
        if (admin.getIsDelete()==IsDelete.是){
            throw new Exception("用户已被禁用！");
        }
        return admin;
    }

    /**
     * 禁用用户
     * @param id
     */
    public void del(Long id) {
        Admin admin = super.find(id);
        if (ShiroUtils.getCurrAdmin().getId().equals(admin.getId())){
            throw new ServiceException("不可禁用自己");
        }
        admin.setIsDelete(IsDelete.values()[(1-admin.getIsDelete().ordinal())]);
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        AdminRealm shiroRealm = (AdminRealm)rsm.getRealms().iterator().next();
        shiroRealm.clearCache(admin.getUsername());
    }

    /**
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    public void grantRoles(Long adminId, Long[] roleIds) {
        Admin admin = super.find(adminId);
        roleService.delRole(admin.getRoleSet(),adminId);
        roleService.addRole(roleIds,adminId);
    }
}
