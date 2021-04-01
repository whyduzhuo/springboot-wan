package com.duzhuo.wansystem.shiro;


import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.wansystem.dao.base.AdminDao;
import com.duzhuo.wansystem.dao.base.MenuDao;
import com.duzhuo.wansystem.dao.base.RoleDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/4/1 14:56
 */
@Service
public class ShiroSourceService extends BaseService<Admin,Long> {


    @Resource
    private AdminDao adminDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private MenuDao menuDao;

    @Resource
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Resource
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Resource
    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    /**
     *
     * @param admin
     * @return
     */
    public Role getCurrRole(Admin admin) {
        Long roleId = adminDao.getCurrRoleId(admin.getId());
        if (roleId != null) {
            return roleDao.findById(roleId).get();
        }

        List<Role> roleList = roleDao.getAllRolesByAdmin(admin.getId());
        if (roleList.isEmpty()){
            return null;
        }
        Role role = roleList.iterator().next();
        adminDao.addDefaultRole(admin.getId(),role.getId());
        return role;
    }

    /**
     *
     * @param roleId
     * @return
     */
    public List<Menu> getMenu(Long roleId) {
        return menuDao.getMenu(roleId);
    }

    /**
     *
     * @param username
     * @return
     */
    public Admin findByUsernameEnable(String username) {
        return adminDao.findByUsernameAndDelTime(username,0L);
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
        if (admin.getDelTime()!=0L){
            throw new Exception("用户已被禁用！");
        }
        return admin;
    }

    private Admin findByUsernameAndPassword(String username, String password) {
        return adminDao.findByUsernameAndPassword(username,password);
    }
}
