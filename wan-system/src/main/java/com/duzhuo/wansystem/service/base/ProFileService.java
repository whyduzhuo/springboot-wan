package com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.wansystem.dao.base.ProFileDao;
import com.duzhuo.wansystem.entity.base.ProFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 项目文件
 * @author: wanhy
 * @date: 2020/1/2 8:51
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ProFileService extends BaseService<ProFile,Long>{
    @Resource
    private ProFileDao proFileDao;
    @Value("${wan.filePath}")
    private String filePath;

    @Resource
    public void setBaseDao(ProFileDao proFileDao){
        super.setBaseDao(proFileDao);
    }

    public void downLoad(@NotNull Long id, HttpServletResponse response) throws IOException{
        ProFile proFile = super.find(id);
        if (proFile==null){
            throw new FileNotFoundException("文件不存在！");
        }
    }
}
