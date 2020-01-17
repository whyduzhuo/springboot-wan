package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.wansystem.dao.base.PositionDao;
import com.duzhuo.wansystem.entity.base.Position;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/1/7 15:50
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PositionService extends BaseService<Position,Long> {
    @Resource
    private PositionDao positionDao;

    @Resource
    public void setBaseDao(PositionDao positionDao){
        super.setBaseDao(positionDao);
    }

    public Message insert(Position position) {
        return Message.error("功能暂未完成！");
    }

    public Message edit(Position position) {
        return Message.error("功能暂未完成！");
    }
}
