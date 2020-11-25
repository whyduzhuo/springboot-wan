package com.duzhuo.wansystem.freemarker.template;

import com.duzhuo.common.config.Global;
import com.duzhuo.common.config.RedisKeyTimeOutConfig;
import com.duzhuo.common.utils.RedisUtils;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import com.duzhuo.wansystem.entity.base.Role;
import com.duzhuo.wansystem.service.base.AdminService;
import com.duzhuo.wansystem.service.base.MenuService;
import com.duzhuo.wansystem.service.base.RoleService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 万宏远
 * @email:1434495271@qq.com
 * @date: 2020/11/14 10:45
 */

@Component
public class MenuDirective implements TemplateDirectiveModel {

    @Resource
    private AdminService adminService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;
    @Resource
    private RedisUtils redisUtils;
    private static String imgTag = "<img src='/static/img/menu.png'/>";

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Admin admin = adminService.getCurrent();
        if (admin!=null){
            Role role = (Role) redisUtils.get(Global.ROLE_SESSION_KEY+admin.getId());
            role = roleService.find(role.getId());
            List<Menu> menuList = role.getMenuSet().stream().sorted().collect(Collectors.toList());
            menuList.removeIf(r->r.getType()== Menu.TypeEnum.按钮);
            List<Ztree> ztreeList = menuService.buildTree(menuList);
            List<Ztree> ztrees = new ArrayList<>();
            menuService.assembleTree(null,ztrees,ztreeList);
            StringBuilder html = new StringBuilder();
            buildHtml(html,ztrees);
            DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_29);
            env.setVariable("navHtml",builder.build().wrap(html));
            body.render(env.getOut());
        }
    }

    /**
     *
     * @param html
     * @param ztreeList
     */
    private void buildHtml(StringBuilder html,List<Ztree> ztreeList){
        ztreeList.forEach(z->{
            if (z.getType().equals(Menu.TypeEnum.目录.toString())){
                html.append("<li><a class = \"nav-submenu\" data-toggle=\"nav-submenu\" data-id='"+z.getUrlPath()+"' href='javascript:void(0)'>"+imgTag+"<span class='sidebar-mini-hide'>"+z.getName()+"</span></a>\n");
            }else {
                html.append("<li><a class = \"menuItem\"  data-id='"+z.getUrlPath()+"' href='javascript:void(0)'><span class='sidebar-mini-hide'>"+z.getName()+"</span></a>\n");
            }
            if (!z.getChildren().isEmpty()){
                html.append("<ul>\n");
                buildHtml(html,z.getChildren());
                html.append("</ul>\n");
            }
            html.append("</li>");
        });
    }


//    /**
//     *
//     * @param html
//     * @param ztreeList
//     */
//    private void buildHtml(StringBuilder html,List<Ztree> ztreeList){
//        ztreeList.forEach(z->{
//            String htmlClass = "menuItem";
//            if (z.getType().equals(Menu.TypeEnum.目录.toString())){
//                htmlClass = "menuDict";
//            }
//            html.append("<li><a class='menu "+htmlClass+"' data-id='"+z.getUrl()+"'><span class='sidebar-mini-hide'>"+z.getName()+"</span></a>\n");
//            if (!z.getChildren().isEmpty()){
//                html.append("<ul class=\"nav-ul \" style=\"display: none\">\n");
//                buildHtml(html,z.getChildren());
//                html.append("</ul></li>\n");
//            }
//        });
//    }
}
