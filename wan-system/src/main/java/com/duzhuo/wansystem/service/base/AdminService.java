package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
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
public class AdminService extends BaseService<Admin,Long>{
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
    public Message insert(Admin admin) {
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
        return adminDao.findByUsernameAndPassword(username,password);
    }
}
