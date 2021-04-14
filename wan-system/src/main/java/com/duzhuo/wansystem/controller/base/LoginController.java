package com.duzhuo.wansystem.controller.base;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.config.RedisKeyTimeOutConfig;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.enums.OperateType;
import com.duzhuo.common.utils.RedisUtils;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RememberMeService;
import com.duzhuo.wansystem.service.base.RoleService;
import com.duzhuo.wansystem.service.base.po.AdminPoService;
import com.duzhuo.wansystem.shiro.AdminRealm;
import com.duzhuo.wansystem.shiro.MyUsernamePasswordToken;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
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
    private AdminPoService adminPoService;
    @Resource
    private RememberMeService rememberMeService;
    @Resource
    private RedisKeyTimeOutConfig redisKeyTimeOutConfig;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private AdminService adminService;
    @Autowired
    private CaptchaService captchaService;

//    @Log(title = "进入登录页",operateType = OperateType.OTHER)
//    @ApiOperation(value = "进入登录页")
//    @GetMapping("/login")
//    public String login(HttpServletRequest request){
//        Admin admin = ShiroUtils.getCurrAdmin();
//        if (admin!=null){
//            return "redirect:/base/index";
//        }
//        Admin cookAdmin = rememberMeService.getCookAdmin(request);
//        if (cookAdmin!=null){
//            MyUsernamePasswordToken token = new MyUsernamePasswordToken(cookAdmin.getUsername(), cookAdmin.getPassword(), MyUsernamePasswordToken.LoginType.REMEMBER_ME);
//            Subject subject = SecurityUtils.getSubject();
//            subject.login(token);
//            return "redirect:/base/index";
//
//        }
//        return "/base/login/login";
//    }

//    @Log(title = "登录系统",operateType = OperateType.OTHER)
//    @ApiOperation(value = "登录系统")
//    @PostMapping("/login")
//    @ResponseBody
//    public Message login(HttpServletRequest request, HttpServletResponse response, String username, String password,
//                         @RequestParam(value = "rememberMe",required = false,defaultValue = "false") Boolean rememberMe,
//                         String kaptchaKey, String kaptchaValInput ) {
//        //校验验证码是否正确
//        if (StringUtils.isBlank(kaptchaValInput)){
//            return Message.warn("请输入验证码");
//        }
//        String kaptchaVal = redisUtils.get(kaptchaKey).toString();
//        if (!kaptchaValInput.equalsIgnoreCase(kaptchaVal)){
//            return Message.warn("验证码不正确");
//        }
//        String p =  DigestUtils.md5Hex(username+password);
//        MyUsernamePasswordToken token = new MyUsernamePasswordToken(username,p, MyUsernamePasswordToken.LoginType.PASSWORD);
//        Subject subject = SecurityUtils.getSubject();
//        try {
//            subject.login(token);
//            rememberMeService.rememberMe(request,response,rememberMe);
//            return Message.success();
//        } catch (AuthenticationException e) {
//            String msg = "用户或密码错误";
//            if (StringUtils.isNotEmpty(e.getMessage())) {
//                msg = e.getMessage();
//            }
//            log.warn(e.getMessage(),e);
//            return Message.error(msg);
//        }
//    }

    @Log(title = "进入登录页",operateType = OperateType.OTHER)
    @ApiOperation(value = "进入登录页")
    @GetMapping("/login")
    public String login(HttpServletRequest request){
        Admin admin = ShiroUtils.getCurrAdmin();
        if (admin!=null){
            return "redirect:/base/index";
        }
        Admin cookAdmin = rememberMeService.getCookAdmin(request);
        if (cookAdmin!=null){
            MyUsernamePasswordToken token = new MyUsernamePasswordToken(cookAdmin.getUsername(), cookAdmin.getPassword(), MyUsernamePasswordToken.LoginType.REMEMBER_ME);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            return "redirect:/base/index";

        }
        return "/base/login/login2";
    }



    @Log(title = "登录系统",operateType = OperateType.OTHER)
    @ApiOperation(value = "登录系统")
    @PostMapping("/login")
    @ResponseBody
    public Message login(HttpServletRequest request, HttpServletResponse response, String username, String password,
                         @RequestParam(value = "rememberMe",required = false,defaultValue = "false") Boolean rememberMe,
                         String captchaVerification) {
        //校验验证码是否正确
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        ResponseModel responseModel = captchaService.verification(captchaVO);
        if(!responseModel.isSuccess()){
            return Message.error("验证失效！");
        }
        String p =  DigestUtils.md5Hex(username+password);
        MyUsernamePasswordToken token = new MyUsernamePasswordToken(username,p, MyUsernamePasswordToken.LoginType.PASSWORD);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            rememberMeService.rememberMe(request,response,rememberMe);
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
    public Message logout(HttpServletRequest request){
        Admin admin = ShiroUtils.getCurrAdmin();
        AdminRealm shiroRealm = ShiroUtils.getShiroRelame();
        shiroRealm.clearMyCache();
        SecurityUtils.getSubject().logout();
        rememberMeService.removeRememberMe(request);
        return Message.success("退出成功！");
    }

    @GetMapping("/refresh")
    public String refush(){
        AdminRealm shiroRealm = ShiroUtils.getShiroRelame();
        shiroRealm.clearCachedAuthorizationInfo();
        return "redirect:/base/index";
    }

    @GetMapping("/refreshAll")
    public String refreshAll(){
        AdminRealm shiroRealm = ShiroUtils.getShiroRelame();
        shiroRealm.clearAllCachedAuthorizationInfo();
        return "redirect:/base/index";
    }


    @GetMapping("/changeRoleWin")
    public String changeRoleWindw(Model model){
        Admin admin = ShiroUtils.getCurrAdmin();
        List<Role> roleList = roleService.getAllRolesByAdmin(admin.getId());
        model.addAttribute("dataList",roleList);
        model.addAttribute("role",adminService.getCurrRole(admin));
        return "/base/login/changeRoleWin";
    }

    @Log(title = "切换角色",operateType = OperateType.OTHER)
    @ApiOperation(value = "切换角色")
    @ResponseBody
    @GetMapping("/changeRole")
    public Message changeRole(Long id){
        Admin admin = ShiroUtils.getCurrAdmin();
        adminService.changeRole(admin.getId(),id);
        return Message.success("切换成功！");
    }
}
