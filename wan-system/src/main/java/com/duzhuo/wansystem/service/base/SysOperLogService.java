package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.Filter;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.core.base.BaseEntity;
import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.ExcelUtils;
import org.apache.commons.lang3.StringUtils;
import com.duzhuo.wansystem.dao.base.SysOperLogDao;
import com.duzhuo.wansystem.entity.base.SysOperLog;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/1/4 11:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOperLogService extends BaseService<SysOperLog,Long> {

    @Resource
    private SysOperLogDao sysOperLogDao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    public void setBaseDao(SysOperLogDao sysOperLogDao){
        super.setBaseDao(sysOperLogDao);
    }

    public Message insert(SysOperLog sysOperLogVO) {
        return Message.success("功能未完成！");
    }

    public Message importData(MultipartFile file) throws IOException {
        List<List<String>> data = ExcelUtils.readExelData(file, ExcelUtils.HEAD_LOCK_ROW,10);
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
        if (StringUtils.isBlank(sysOperLog.getTitle())){
            throw new ServiceException("title can not be null");
        }
        if (StringUtils.isBlank(sysOperLog.getMethod())){
            throw new ServiceException("method can not be null");
        }
        super.save(sysOperLog);
        return Message.success("添加成功！");
    }

    public Message edit(SysOperLog sysOperLog) {
        super.update(sysOperLog);
        return Message.success("修改成功！");
    }

    /**
     * 倒数数据
     * @param response
     * @param filters
     * @param fields
     */
    public void exportData(HttpServletResponse response, List<Filter> filters, String[] fields) throws Exception {
        String fileName="日志信息导出";
        long count = super.count(filters);
        if (count>100000){
            throw new ServiceException("数据量过大，请增加筛选条件，降至100000条以下！");
        }
        List<SysOperLog> logList = super.searchList(filters,Sort.by(Sort.Direction.DESC, BaseEntity.CREATE_DATE_PROPERTY_NAME));
        List<Map<String,Object>> data = new ArrayList<>(logList.size());
        for (SysOperLog operLog:logList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",operLog.getId());
            map.put("时间", DateFormatUtils.format(operLog.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
            map.put("用户",operLog.getAdmin()==null?"":operLog.getAdmin().getRealname());
            map.put("IP",operLog.getOperIp());
            map.put("操作",operLog.getTitle());
            map.put("请求地址",operLog.getOperUrl());
            map.put("操作类型",operLog.getOperateType().toString());
            map.put("请求方式",operLog.getMethod());
            map.put("是否成功",operLog.getStatus());
            map.put("是否报错",operLog.getHaveException());
            map.put("请求参数",operLog.getOperParm());
            map.put("异常信息",operLog.getErrorMsg());
            map.put("响应结果",operLog.getJsonResult());
            data.add(map);
        }
        ExcelUtils.exportExcel(fileName, fields, data, response);
    }
}
