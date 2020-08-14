package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.MenuDao;
import com.duzhuo.wansystem.dto.Ztree;
import com.duzhuo.wansystem.entity.base.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: wanhy
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
    public Message addData(Menu menuVO) {
        if (menuVO.getParent()!=null && menuVO.getParent().getId()!=null){
            menuVO.setParent(super.find(menuVO.getParent().getId()));
        }
        if (menuVO.getNum()==null){
            Long num = this.createId(menuVO.getParent());
            menuVO.setNum(num);
        }
        this.check(menuVO);
        super.save(menuVO);
        return Message.success("添加成功！",this.menuToTree(menuVO));
    }

    /**
     * 菜单--字段校验
     * @param menuVO
     */
    private void check(Menu menuVO){
        if (StringUtils.isBlank(menuVO.getName())){
            throw new ServiceException("菜单名不能为空！");
        }
        if (menuVO.getOs()==null){
            throw new ServiceException("OS不能为空");
        }
        if (StringUtils.isBlank(menuVO.getPath())){
            throw new ServiceException("请输入url");
        }
        if (menuVO.getType()==null){
            throw new ServiceException("菜单类型不能为空");
        }
        if (menuVO.getIsEnable()==null){
            throw new ServiceException("启用or禁用？");
        }
        if (menuVO.getParent()==null){
            if (menuVO.getType()==Menu.Type.按钮){
                throw new ServiceException("一级菜单不能为按钮！");
            }
        }else {
            if (menuVO.getParent().getType()==Menu.Type.目录){
                if (menuVO.getType()==Menu.Type.按钮){
                    throw new ServiceException("目录下不能为按钮！");
                }
            }
            if (menuVO.getParent().getType()==Menu.Type.页面){
                if (menuVO.getType()!=Menu.Type.按钮){
                    throw new ServiceException("页面上只能加按钮！");
                }
            }
            if (menuVO.getParent().getType()==Menu.Type.按钮){
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
     * @param menu
     * @return
     */
    public Message edit(Menu menu) {
        this.check(menu);
        super.update(menu);
        return Message.success("添加成功！");
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
        return menuDao.haveChriden(parendId).compareTo(new BigDecimal("0"))>0;
    }

    /**
     * 创建菜单树--根据自己已有的菜单
     * @param menus
     * @return
     */
    public List<Menu> buildMenu(List<Menu> menus){
        List<Menu> menuList = this.findRoot(menus);
        menuList.forEach(m->m.setChildren(findChird(m,menus)));
        return menuList;
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
     * 找儿子,找到之后移除
     * @param menu
     * @param menus
     * @return
     */
    public List<Menu> findChird(Menu menu,List<Menu> menus){
        List<Menu> menuList = new ArrayList<>();
        if (menus.isEmpty()){
            return menuList;
        }
        Iterator<Menu> it = menus.iterator();
        while (it.hasNext()){
            Menu m = it.next();
            if (m.getParent()==null||!m.getParent().getId().equals(menu.getId())){
                continue;
            }
            if (m.getType()==Menu.Type.页面){
                menuList.add(m);
            }else if (m.getType()==Menu.Type.目录){
                m.setChildren(findChird(m,menus));
            }
        }
        return menuList;
    }

    /**
     *
     * @param menu
     * @return
     */
    public Ztree menuToTree(Menu menu){
        Ztree ztree = new Ztree();
        ztree.setId(menu.getId());
        ztree.setName(menu.getName());
        ztree.setPid(menu.getParent()==null?null:menu.getParent().getId());
        ztree.setTitle(menu.getRemark());
        ztree.setNum(menu.getNum().toString());
        ztree.setOpen(true);
        if (menu.getType()==Menu.Type.按钮){
            ztree.setIcon(Ztree.buttonIcon);
        }
        if (menu.getType()==Menu.Type.页面){
            ztree.setIcon(Ztree.pageIcon);
        }
        ztree.setType(menu.getType().toString());
        return ztree;
    }

    /**
     * 将menus转ztree对象
     * @param menus
     * @return
     */
    public List<Ztree> buildTree(List<Menu> menus) {
        List<Ztree> ztreeList = new ArrayList<>();
        menus.forEach(m->ztreeList.add(menuToTree(m)));
        return ztreeList;
    }

    /**
     *
     * @return
     */
    public Integer getMaxOrder(Long parentId){
        return menuDao.getMaxOrder(parentId).intValue();
    }
}
