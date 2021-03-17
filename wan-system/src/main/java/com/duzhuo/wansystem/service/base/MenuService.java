package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.MenuDao;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/7 15:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuService extends BaseService<Menu,Long> {
    private static final Long MAX_NUM=99L;

    @Resource
    private MenuDao menuDao;

    @Resource
    public void setBaseDao(MenuDao menuDao){
        super.setBaseDao(menuDao);
    }

    /**
     * 菜单--新增
     * @param menuVO
     * @return
     */
    public void addData(Menu menuVO) {
        if (menuVO.getParent()==null){
            addTopMenu(menuVO);
            return;
        }
        if (menuVO.getParent()!=null && menuVO.getParent().getId()!=null){
            menuVO.setParent(super.find(menuVO.getParent().getId()));
        }
        if (menuVO.getNum()==null){
            Long num = this.createId(menuVO.getParent());
            menuVO.setNum(num);
        }
        if (menuVO.getType()==Menu.TypeEnum.页面组){
            menuVO.setPath("/base/menu/menuGroup?num="+menuVO.getNum());
        }
        super.validation(menuVO);
        this.check(menuVO);
        super.save(menuVO);
    }

    /**
     * 添加顶层菜单
     * @param menuVO
     */
    public void addTopMenu(Menu menuVO){
        menuVO.setPath("#");
        menuVO.setType(Menu.TypeEnum.目录);
        super.validation(menuVO);
        super.save(menuVO);
    }

    /**
     * 菜单--字段的逻辑校验
     * @param menuVO
     */
    private void check(Menu menuVO){
        if (menuVO.getParent()==null){
            if (menuVO.getType()==Menu.TypeEnum.按钮){
                throw new ServiceException("一级菜单不能为按钮！");
            }
        }else {
            if (menuVO.getParent().getType()==Menu.TypeEnum.目录){
                if (menuVO.getType()==Menu.TypeEnum.按钮){
                    throw new ServiceException("目录下不能为按钮！");
                }
            }
            if (menuVO.getParent().getType()==Menu.TypeEnum.页面){
                if (menuVO.getType()!=Menu.TypeEnum.按钮){
                    throw new ServiceException("页面上只能加按钮！");
                }
            }
            if (menuVO.getParent().getType()==Menu.TypeEnum.按钮){
                throw new ServiceException("按钮下不可加东西！");
            }
        }
    }

    /**
     * 菜单id生成
     * 一级菜单生成规则：10，11，12，13....99
     * 二级菜单生成规则：1001，1002，1003....1099,其中前两位是父菜单id
     * 三级菜单生成规则：100101，100102，100103...100199,其中前四位是父菜单id
     * 已此类推
     * @param parent
     * @return
     */
    public Long createId(Menu parent){
        //一级菜单
        if (parent==null || parent.getId()==null){
            Long num= this.getMaxTop();
            if (num==null){
                return 10L;
            }
            if (num.equals(MAX_NUM)){
                throw new ServiceException("菜单编号越界");
            }
            return num+1;
        }
        //n级菜单
        Long maxBother= this.getMaxBother(parent.getId());
        if (maxBother==null){
            return parent.getNum()*100;
        }
        if (MAX_NUM.equals(maxBother%100)){
            throw new ServiceException("菜单编号越界");
        }
        return maxBother+1;
    }

    /**
     * 查询最大的兄弟节点num
     * @param parentId
     * @return
     */
    private Long getMaxBother(Long parentId){
        BigDecimal bigDecimal= menuDao.getMaxBother(parentId);
        if (bigDecimal==null){
            return null;
        }
        return bigDecimal.longValue();
    }

    /**
     * 获取最大的根节点编号
     * @return
     */
    private Long getMaxTop(){
        BigDecimal bigDecimal= menuDao.getMaxTop();
        if (bigDecimal==null){
            return null;
        }
        return bigDecimal.longValue();
    }

    /**
     * 菜单管理--编辑修改
     * @param menuVo
     * @return
     */
    public void edit(Menu menuVo) {
        super.validation(menuVo);
        this.check(menuVo);
        Menu menu = super.find(menuVo.getId());
        menu.setName(menuVo.getName());
        menu.setIsEnable(menuVo.getIsEnable());
        menu.setNum(menuVo.getNum());
        menu.setOrder(menuVo.getOrder());
        menu.setPath(menuVo.getPath());
        menu.setRemark(menuVo.getRemark());
        menu.setType(menuVo.getType());
        super.update(menu);
    }

    /**
     * 菜单id
     * @param id
     */
    @Override
    public void delete(Long id){
        //如果有子节点，不允许删除
        if (this.haveChriden(id)){
            throw new ServiceException("菜单存在子节点，请先删除子节点！");
        }
        //先删除菜单-角色关联
        this.delMenuRole(id);
        super.delete(id);
    }

    /**
     * 批量删除菜单
     * @param ids
     */
    @Override
    public void delete(Long... ids){
        Arrays.stream(ids).forEach(this::delete);
    }

    /**
     * 删除菜单角色关联
     * @param menuId
     */
    public void delMenuRole(Long menuId){
        menuDao.delMenuRole(menuId);
    }

    /**
     * 查询是否有子节点
     * @return
     */
    public Boolean haveChriden(Long parendId){
        return menuDao.haveChriden(parendId).compareTo(BigDecimal.ZERO)>0;
    }


    /**
     * 找根节点
     * @param menus
     * @return
     */
    public List<Menu> findRoot(List<Menu> menus){
        List<Menu> menuList = new ArrayList<>();
        menus.forEach(m->{
            if (m.getParent()==null){
                menuList.add(m);
            }
        });
        return menuList;
    }

    /**
     * 查询全部菜单的根节点
     * @return
     */
    public List<Menu> findRoot(){
        return menuDao.findByParentIsNullOrderByOrder();
    }


    /**
     * 将菜单转tree对象
     * 不可勾选
     * @param menu
     * @return
     */
    public Ztree toTree(Menu menu){
        Ztree ztree = new Ztree();
        ztree.setId(menu.getId());
        ztree.setName(menu.getName());
        ztree.setPid(menu.getParent()==null?null:menu.getParent().getId());
        ztree.setTitle(menu.getRemark());
        ztree.setNum(menu.getNum().toString());
        ztree.setOpen(true);
        ztree.setOrders(menu.getOrder());
        if (menu.getType()==Menu.TypeEnum.按钮){
            ztree.setIcon(Menu.BUTTON_ICON);
        }
        if (menu.getType()==Menu.TypeEnum.页面组){
            ztree.setIcon(Menu.PAGE_GROUP_ICON);
        }
        if (menu.getType()==Menu.TypeEnum.页面){
            ztree.setIcon(Menu.PAGE_ICON);
            ztree.setOpen(false);
        }
        ztree.setUrlPath(menu.getPath());
        ztree.setType(menu.getType().toString());
        return ztree;
    }

    /**
     * 将menus转ztree对象
     * @param menus
     * @return
     */
    public List<Ztree> toTree(List<Menu> menus) {
        List<Ztree> ztreeList = new ArrayList<>();
        menus.forEach(m->ztreeList.add(toTree(m)));
        return ztreeList;
    }

    /**
     *
     * @return
     */
    public Integer getMaxOrder(Long parentId){
        return menuDao.getMaxOrder(parentId).intValue();
    }

    /**
     *
     * @return
     */
    public Integer getMaxOrder(){
        return menuDao.getMaxOrder().intValue();
    }

    /**
     * 创建菜单树
     * @param allMenuList 全部菜单
     * @param menuList 已勾选菜单
     * @return
     */
    public List<Ztree> buildSelectMenu(List<Menu> allMenuList, List<Menu> menuList) {
        List<Ztree> allMenuListTree = this.toTree(allMenuList);
        List<Ztree> menuListTree = this.toTree(menuList);
        return Ztree.buildTree(allMenuListTree,menuListTree);
    }

    /**
     * 给某角色授予菜单权限
     * @param roleId
     * @param menuId
     */
    public void grantMenu(Long roleId,Long menuId){
        if (roleId==null){
            throw new ServiceException("roleId can not be bull");
        }
        if (menuId==null){
            throw new ServiceException("menuId can not be null");
        }
        if (menuDao.hasMenu(roleId,menuId).compareTo(BigDecimal.ZERO)>0){
            return;
        }
        menuDao.grantMenu(roleId,menuId);
    }

    /**
     * 给某角色授予菜单权限
     * @param roleId
     * @param menusId
     */
    public void grantMenu(Long roleId,Collection<Long> menusId){
        if (roleId==null) {
            throw new ServiceException("roleId can not be bull");
        }
        if (menusId!=null && menusId.size()>0){
            menusId.forEach(m->grantMenu(roleId,m));
        }
    }

    /**
     * 删除某角色下的菜单
     * @param roleId
     * @param menuIds
     */
    public void delMenu(Long roleId, Collection<Long> menuIds){
        menuIds.forEach(menuId->this.delMenu(roleId,menuId));
    }

    /**
     * 删除某角色的某个菜单
     * @param roleId
     * @param menuId
     */
    public void delMenu(Long roleId,Long menuId){
        if (roleId==null){
            throw new ServiceException("roleId can not be bull");
        }
        if (menuId==null){
            throw new ServiceException("menuId can not be null");
        }
        if (menuDao.hasMenu(roleId,menuId).compareTo(BigDecimal.ZERO)==0){
            return;
        }
        menuDao.delMenu(roleId,menuId);
    }

    /**
     * 删除某角色的全部菜单
     * @param roleId
     */
    public void delAllMenu(Long roleId){
        if (roleId==null){
            throw new ServiceException("roleId can not be null");
        }
        menuDao.delAllMenu(roleId);
    }

    /**
     * 查询所有的父节点，包括自己
     * @param menuId
     * @return
     */
    public List<Menu> getAllParent(Long menuId){
        return menuDao.getAllParent(menuId);
    }

    /**
     * 查询某个用户的全部菜单
     * @param admin
     * @return
     */
    public List<Menu> getMenuList(Admin admin) {
        return menuDao.getMenuList(admin);
    }


    /**
     *
     * @param num
     */
    public Menu findByNum(Long num) {
        return menuDao.findByNum(num);
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Menu> getChildren(Long id) {
        return menuDao.findByParentId(id);
    }
}
