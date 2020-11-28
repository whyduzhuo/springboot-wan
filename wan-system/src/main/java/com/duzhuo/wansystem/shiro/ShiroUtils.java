package com.duzhuo.wansystem.shiro;

import com.duzhuo.common.utils.BeanUtils;
import com.duzhuo.common.utils.Tools;
import com.duzhuo.wansystem.entity.base.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/4 11:51
 */

public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static Admin getCurrAdmin() {
        Admin admin = null;
        Object obj = getSubject().getPrincipal();
        if (Tools.vaildeParam(obj)) {
            admin = new Admin();
            BeanUtils.copyBeanProp(admin, obj);
        }
        return admin;
    }

    /**
     * 获取IP
     * @return
     */
    public static String getIp() {
        return getSubject().getSession().getHost();
    }
}
