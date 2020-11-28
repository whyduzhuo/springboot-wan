package com.duzhuo.wansystem.service.daily;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.wansystem.dao.daily.IssueDao;
import com.duzhuo.wansystem.entity.daily.Issue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/5/19 18:03
 */
@Service
public class IssueService extends BaseService<Issue,Long>{
    @Resource
    private IssueDao issueDao;

    @Resource
    public void setBaseDao(IssueDao issueDao){
        super.setBaseDao(issueDao);
    }

    /**
     * 创建任务
     * @param issueVO
     * @return
     */
    public Message addData(Issue issueVO) {
        throw new ServiceException("功能未完成！");
    }
}
