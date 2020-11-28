package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.config.Global;
import com.duzhuo.common.config.RedisKeyTimeOutConfig;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.RedisUtils;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import com.duzhuo.wansystem.shiro.AdminRealm;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author: wanhy
 * @date: 2020/1/7 18:06
 */
@Slf4j
@Controller
@Api(tags = "登录管理")
@RequestMapping("/base")
public class LoginController {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private RedisKeyTimeOutConfig redisKeyTimeOutConfig;
    @Resource
    private RedisUtils redisUtils;

    @Log(title = "进入登录页",operateType = OperateType.OTHER)
    @ApiOperation(value = "进入登录页")
    @GetMapping("/login")
    public String login(){
        Admin admin = ShiroUtils.getCurrAdmin();
        if (admin!=null){
            return "redirect:/base/index";
        }
        return "/base/login/login";
    }

    @Log(title = "登录系统",operateType = OperateType.OTHER)
    @ApiOperation(value = "登录系统")
    @PostMapping("/login")
    @ResponseBody
    public Message login(String username, String password, Boolean rememberMe, HttpServletRequest request) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            Admin admin =ShiroUtils.getCurrAdmin();
            Set<Role> roleSet = admin.getRoleSet();
            if (roleSet.isEmpty()){
                subject.logout();
                return Message.error("您还没有角色信息，请联系管理员！");
            }
            redisUtils.set(Global.ROLE_SESSION_KEY+admin.getId(),roleSet.iterator().next());
            return Message.success();
        } catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            log.warn(e.getMessage(),e);
            return Message.error(msg);
        }
    }

    @Log(title = "进入首页",operateType = OperateType.SELECT)
    @ApiOperation(value = "进入首页")
    @GetMapping("/index")
    public String index(Model model){
        Admin admin = ShiroUtils.getCurrAdmin();
        model.addAttribute("admin",admin);
        return "/base/login/index";
    }

    @Log(title = "进入错误页面",operateType = OperateType.OTHER)
    @ApiOperation(value = "进入错误页面")
    @GetMapping("/error")
    @ResponseBody
    public String error(){
        return "请先登录！";
    }

    @Log(title = "退出登录",operateType = OperateType.OTHER)
    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    @ResponseBody
    public Message logout(){
        Admin admin = ShiroUtils.getCurrAdmin();
        SecurityUtils.getSubject().logout();
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        AdminRealm shiroRealm = (AdminRealm)rsm.getRealms().iterator().next();
        shiroRealm.clearMyCache();
        redisUtils.delete(Global.ROLE_SESSION_KEY+admin.getId());
        return Message.success("退出成功！");
    }

    @GetMapping("/refresh")
    public String refush(){
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        AdminRealm shiroRealm = (AdminRealm)rsm.getRealms().iterator().next();
        shiroRealm.clearCachedAuthorizationInfo();
        return "redirect:/base/index";
    }

    @GetMapping("/refreshAll")
    public String refreshAll(){
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        AdminRealm shiroRealm = (AdminRealm)rsm.getRealms().iterator().next();
        shiroRealm.clearAllCachedAuthorizationInfo();
        return "redirect:/base/index";
    }
}
