package com.duzhuo.wansystem.shiro;

import com.duzhuo.common.utils.RedisUtils;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.crazycake.shiro.RedisCacheManager;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Realm 处理登录 权限
 * 
 * @author 万宏远
 * @email: 1434495271@qq.com
 */
@Slf4j
public class AdminRealm extends AuthorizingRealm {

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    @Resource
    private AdminService adminService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        Admin admin = ShiroUtils.getCurrAdmin();
        // 防止认证缓存问题导致授权不起效果
        admin = adminService.find(admin.getId());
        // 职务列表
        Set<String> roleSet = admin.getRoleSet().stream().map(Role::getName).collect(Collectors.toSet());
        // 菜单/功能列表
        Set<Menu> menus = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加菜单
        admin.getRoleSet().forEach(role -> menus.addAll(role.getMenuSet()));
        Set<String> permissions = new HashSet<>();
        menus.forEach(m->permissions.add(m.getNum().toString()));
        // 角色加入AuthorizationInfo认证对象，在controller接口加RequiresRoles 就可以用了
        info.setRoles(roleSet);
        // 权限加入AuthorizationInfo认证对象,在controller接口加RequiresPermissions 就可以用了
        info.setStringPermissions(permissions);

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
        Admin admin;
        try {
            admin = adminService.login(username, password);
            admin.setRole(adminService.getCurrRole(admin));
        } catch (Exception e) {
            log.info(e.getMessage(),e);
            throw new AuthenticationException(e.getMessage(), e);
        }
        return new SimpleAuthenticationInfo(admin, password, getName());
    }

    /**
     * 清除自己的认证缓存
     */
    public void clearCachedAuthenticationInfo() {
        super.clearCachedAuthenticationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 清除某人的认证缓存
     * @see RedisCacheManager#keyPrefix
     * 因为缓存key值自定义的用户username {@link ShiroConfig#myShiroRealm()} {@link ShiroConfig#cacheManager}
     */
    public void clearCachedAuthenticationInfo(String username) {
        String key = "shiro:cache:authenticationCache:"+username;
        redisUtils.delete(key);
    }

    /**
     * 清除全部认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }


    /**
     * 清除自己的授权缓存
     */
    public void clearCachedAuthorizationInfo(){
        clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }

    /**
     * 清除某人的授权缓存
     * @see RedisCacheManager#keyPrefix
     * 因为缓存key值自定义的用户username {@link ShiroConfig#myShiroRealm()} {@link ShiroConfig#cacheManager}
     */
    public void clearCachedAuthorizationInfo(String username){
        String key = "shiro:cache:authorizationCache:"+username;
        redisUtils.delete(key);
    }

    /**
     * 清除全部授权缓存
     */
    public void clearAllCachedAuthorizationInfo(){
        getAuthorizationCache().clear();
    }

    /**
     * 清除自己的 认证缓存  和 授权缓存
     */
    public void clearMyCache() {
        clearCachedAuthenticationInfo();
        clearCachedAuthorizationInfo();
    }

    /**
     * 清除某个人的 认证缓存  和 授权缓存
     * @see RedisCacheManager#keyPrefix
     */
    public void clearCache(String username) {
        clearCachedAuthenticationInfo(username);
        clearCachedAuthorizationInfo(username);
    }

    /**
     * 清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
