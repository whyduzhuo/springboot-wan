package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.base.MenuDao;
import com.duzhuo.wansystem.entity.base.Menu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuService extends BaseService<Menu,Long> {
    private Long MAX_NUM=99L;

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
    public Message insert(Menu menuVO) {
        Long num = this.createId(menuVO.getParent());
        menuVO.setNum(num);
        this.check(menuVO);
        super.save(menuVO);
        return Message.success("添加成功！");
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
        if (menuVO.getType()==null){
            throw new ServiceException("菜单类型不能为空");
        }
        if (menuVO.getIsEnable()==null){
            throw new ServiceException("启用or禁用？");
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
    private Long createId(Menu parent){
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
        parent = super.find(parent.getId());
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
}
