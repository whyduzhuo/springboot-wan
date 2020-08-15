package com.duzhuo.wansystem.controller.base;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author: wanhy
 * @date: 2020/1/7 18:06
 */

@Controller
@Api(tags = "登录管理")
@RequestMapping("/base")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;

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
    public Message login(String username, String password, Boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return Message.success();
        }
        catch (AuthenticationException e) {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage())) {
                msg = e.getMessage();
            }
            logger.warn("用户名或密码错误！",e);
            return Message.error(msg);
        }
    }

    @Log(title = "进入首页",operateType = OperateType.SELECT)
    @ApiOperation(value = "进入首页")
    @GetMapping("/index")
    public String index(Model model){
        Admin admin = ShiroUtils.getCurrAdmin();
        List<Role> roleList = admin.getRoleList();
        List<Menu> menus = roleService.getMenus(roleList);
        Collections.sort(menus);
        List<Menu> menuList = menuService.buildMenu(menus);
        model.addAttribute("menuList",menuList);
        model.addAttribute("admin",admin);
        model.addAttribute("roleList",roleList);
        return "/base/login/main";
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
        SecurityUtils.getSubject().logout();
        return Message.success("退出成功！");
    }
}
