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

    @Override
    public Menu save(Menu menu){
        int row= menuDao.save(menu.getId(),new Date(),new Date(),menu.getName(),menu.getPath(),menu.getParent().getId(),
                menu.getOs().ordinal(),menu.getType().ordinal(),menu.getIsEnable().ordinal(),menu.getOrder());
        if (row==1){
            return menu;
        }
        return null;
    }

    public Message insert(Menu menuVO) {
        Long id = this.createId(menuVO.getParent()==null?null:menuVO.getParent().getId());
        menuVO.setId(id);
        this.check(menuVO);
        this.save(menuVO);
        return Message.success("添加成功！");
    }

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
     * @param parentId
     * @return
     */
    private Long createId(Long parentId){
        //一级菜单
        if (parentId==null){
            Long id= this.getMaxTop();
            if (id==null){
                return 10L;
            }
            if (id.equals(MAX_NUM)){
                throw new ServiceException("id越界");
            }
            return id+1;
        }
        //n级菜单
        Long maxBother= this.getMaxBother(parentId);
        if (maxBother==null){
            return parentId*100;
        }
        if (MAX_NUM.equals(maxBother%100)){
            throw new ServiceException("id越界");
        }
        return maxBother+1;
    }

    /**
     * 查询最大的兄弟节点id
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
     * 获取最大的根节点id
     * @return
     */
    private Long getMaxTop(){
        BigDecimal bigDecimal= menuDao.getMaxTop();
        if (bigDecimal==null){
            return null;
        }
        return bigDecimal.longValue();
    }


    public Message edit(Menu menu) {
        return Message.error("功能暂未完成！");
    }
}
