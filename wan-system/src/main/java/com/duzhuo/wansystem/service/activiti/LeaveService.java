package com.duzhuo.wansystem.service.activiti;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.activiti.LeaveDao;
import com.duzhuo.wansystem.entity.activiti.Leave;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * 请假申请--Service
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/03/11 16:28:18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LeaveService extends BaseService<Leave, Long> {

    @Resource
    private LeaveDao leaveDao;
    @Resource
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;

    @Resource
    public void setBaseDao(LeaveDao leaveDao){
        super.setBaseDao(leaveDao);
    }

    /**
     * 请假申请--数据校验
     * @param leaveVO
     */
    private void check(Leave leaveVO){
        //throw new ServiceException("功能暂未完成！");
    }

    /**
     * 请假申请--新增
     * @param leaveVO
     * @return
     */
    public void addData(Leave leaveVO) {
        super.validation(leaveVO);
        this.check(leaveVO);
        super.save(leaveVO);
    }


    /**
     * 请假申请--修改
     * @param leaveVO
     * @return
     */
    public void edit(Leave leaveVO) {
        super.validation(leaveVO);
        this.check(leaveVO);
        Leave leave = super.find(leaveVO.getId());
        super.update(leave);
    }

    /**
     * 请假申请--删除
     * @param id
     * @return
     */
    public void del(Long id) {
        if (id==null){
            throw new ServiceException("请选择数据！");
        }
        super.delete(id);
    }

    /**
     * 启动流程
     * @param id
     */
    public void submitApply(Long id) {
        Leave leave = super.find(id);
        // 实体类 ID，作为流程的业务 key
        String businessKey = leave.getId().toString();
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(Leave.PROC_DEF_KEY, businessKey, new HashMap<>());
        leave.setInstanceId(instance.getProcessInstanceId());
    }
}
