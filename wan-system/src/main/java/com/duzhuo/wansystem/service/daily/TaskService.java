package com.duzhuo.wansystem.service.daily;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.wansystem.dao.daily.TaskDao;
import com.duzhuo.wansystem.entity.daily.Task;

import javax.annotation.Resource;

/**
 * @author: wanhy
 * @date: 2020/5/19 18:03
 */

public class TaskService extends BaseService<Task,Long>{
    @Resource
    private TaskDao taskDao;

    @Resource
    public void setBaseDao(TaskDao taskDao){
        super.setBaseDao(taskDao);
    }

}
