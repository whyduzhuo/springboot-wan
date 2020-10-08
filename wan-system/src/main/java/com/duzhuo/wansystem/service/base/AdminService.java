package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.common.utils.StringUtils;
import com.duzhuo.wansystem.dao.base.AdminDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public void setBaseDao(AdminDao adminDao){
        super.setBaseDao(adminDao);
    }

    /**
     * 新增用户
     * @param admin
     * @return
     */
    public Message addData(Admin admin) {
        super.save(admin);
        return Message.warn("功能未完成！");
    }

    /**
     * 修改用户
     * @param admin
     * @return
     */
    public Message edit(Admin admin){
        return Message.warn("功能未完成！");
    }

    public Admin getCurrent(){
        return ShiroUtils.getCurrAdmin();
    }

    public Admin findByUsernameAndPassword(String username, String password) {
        return adminDao.findByUsernameAndPasswordAndIsDelete(username,password, IsDelete.否);
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
        return admin;
    }

    /**
     *
     * @param id
     */
    public Message del(Long id) {
        Admin admin = super.find(id);
        admin.setIsDelete(IsDelete.是);
        return Message.success("删除成功！");
    }
}
