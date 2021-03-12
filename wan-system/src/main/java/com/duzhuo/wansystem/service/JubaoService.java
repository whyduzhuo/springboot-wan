package com.duzhuo.wansystem.service;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.wansystem.dao.JubaoDao;
import com.duzhuo.wansystem.entity.Jubao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2021/3/12 16:11
 */
@Service
public class JubaoService extends BaseService<Jubao,Long> {
    @Resource
    private JubaoDao jubaoDao;

    @Resource
    public void setBaseDao(JubaoDao jubaoDao){
        super.setBaseDao(jubaoDao);
    }

    public void addData(Jubao jubao){
        super.save(jubao);
    }

    public void addData(List<Jubao> dataList){
        super.saveAll(dataList);
    }
}
