package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.ExcelUtils;
import com.duzhuo.common.utils.Tools;
import com.duzhuo.wansystem.dao.base.SysOperLogDao;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/1/4 11:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOperLogService extends BaseService<SysOperLog,Long>{

    @Resource
    private SysOperLogDao sysOperLogDao;

    @Resource
    public void setBaseDao(SysOperLogDao sysOperLogDao){
        super.setBaseDao(sysOperLogDao);
    }

    public Message insert(SysOperLog sysOperLogVO) {
        return Message.success("功能未完成！");
    }

    public Message importData(MultipartFile file) throws IOException {
        List<List<String>> data = ExcelUtils.excelToList(file, 2);
        for (List<String> row:data) {
            System.err.println(row+"\t");
        }
        return Message.warn("测试");
    }

    /**
     * 新增数据
     * @param sysOperLog
     */
    public Message addData(SysOperLog sysOperLog) {
        if (!Tools.vaildeParam(sysOperLog.getTitle())){
            throw new ServiceException("title can not be null");
        }
        if (!Tools.vaildeParam(sysOperLog.getMethod())){
            throw new ServiceException("method can not be null");
        }
        super.save(sysOperLog);
        return Message.success("添加成功！");
    }
}
