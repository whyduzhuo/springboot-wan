package com.duzhuo.wansystem.shiro;

import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Position;
import com.duzhuo.wansystem.service.base.LoginService;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.PositionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author ruoyi
 */
public class AdminRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(AdminRealm.class);

    @Resource
    private MenuService menuService;

    @Resource
    private PositionService positionService;

    @Resource
    private LoginService loginService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        Admin admin = ShiroUtils.getCurrAdmin();
        // 职务列表
        Set<Position> positionSet = admin.getPositionSet();
        // 菜单/功能列表
        Set<Menu> menus = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加菜单
        positionSet.stream().forEach(position -> {
            menus.addAll(position.getMenuSet());
        });
        // 角色加入AuthorizationInfo认证对象
//        info.setRoles(positionSet);
        // 权限加入AuthorizationInfo认证对象
//        info.setStringPermissions(menus);

        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        Admin admin = null;
        try {
            admin = loginService.login(username, password);
        } catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(admin, password, getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }


}
