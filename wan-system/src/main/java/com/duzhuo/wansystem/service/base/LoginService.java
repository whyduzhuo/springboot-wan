package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.utils.Tools;
import com.duzhuo.wansystem.entity.base.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/1/7 17:23
 */

@Service
public class LoginService {
    @Resource
    private AdminService adminService;

    public Admin login(String username, String password) throws Exception {
        if (!Tools.vaildeParam(username)){
            throw new Exception("用户名为空！");
        }
        if (!Tools.vaildeParam(password)){
            throw new Exception("密码为空！");
        }
        Admin admin = adminService.findByUsernameAndPassword(username,password);
        if (admin==null){
            throw new Exception("用户名或密码错误！");
        }
        return admin;
    }

}
