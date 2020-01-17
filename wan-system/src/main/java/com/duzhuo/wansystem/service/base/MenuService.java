package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.wansystem.dao.base.MenuDao;
import com.duzhuo.wansystem.entity.base.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:52
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuService extends BaseService<Menu,Long> {
    @Resource
    private MenuDao menuDao;

    @Resource
    public void setBaseDao(MenuDao menuDao){
        super.setBaseDao(menuDao);
    }

    public Message insert(Menu menu) {
        return Message.error("功能暂未完成！");
    }

    public Message edit(Menu menu) {
        return Message.error("功能暂未完成！");
    }
}
