package com.duzhuo.wansystem.service.base.po;

import com.duzhuo.common.annotation.Log;
import com.duzhuo.common.core.del.DeleteService;
import com.duzhuo.common.utils.ServletUtils;
import com.duzhuo.wansystem.dao.base.po.AdminPoDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.po.AdminPo;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 *
 * @date: 2021/1/20 14:27
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminPoService extends DeleteService<AdminPo,Long> {


    @Resource
    private AdminPoDao adminPoDao;

    @Resource
    public void setBaseDao(AdminPoDao adminPoDao){
        super.setBaseDao(adminPoDao);
    }
}
