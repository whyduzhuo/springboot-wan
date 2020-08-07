package ${data.servicepackage};

import com.jxyunge.exception.BusinessException;
import com.jxyunge.enums.ApiResultEnum;
import ${data.entityPackages};
import ${data.daopackage}.${data.entityName}Mapper;
import com.jxyunge.utils.ApiResultUtil;
import org.springframework.stereotype.Service;
import com.jxyunge.utils.ApiResultUtil;
import com.jxyunge.mybatis.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * ${data.module}--Service
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${data.entityName}Service extends BaseService<${data.entityName}Mapper, ${data.entityName}>{

    /**
     * ${data.module}--数据校验
     * @param ${data.lowEntityName}VO
     */
    private void check(${data.entityName} ${data.lowEntityName}VO){
        throw new BusinessException("功能暂未完成！");
    }

    /**
     * ${data.module}--新增
     * @param ${data.lowEntityName}VO
     * @return
     */
    @Override
    public boolean save(${data.entityName} ${data.lowEntityName}VO) {
        this.check(${data.lowEntityName}VO);
        return super.save(${data.lowEntityName}VO);
    }


    /**
     * ${data.module}--修改
     * @param ${data.lowEntityName}VO
     * @return
     */
    @Override
    public boolean update(${data.entityName} ${data.lowEntityName}VO) {
        this.check(${data.lowEntityName}VO);
        ${data.entityName} ${data.lowEntityName} = super.getById(${data.lowEntityName}VO.getId());

        return super.update(${data.lowEntityName});
    }

    /**
     * ${data.module}--删除
     * @param id
     * @return
     */
    public void del(Long id) {
        if (id==null){
            throw new BusinessException("请选择数据！");
        }
        super.delete(id);
    }

    /**
     * ${data.module}--批量删除
     * @param ids
     * @return
     */
    @Override
    public void delete(Long... ids) {
        if (ids==null || ids.length==0){
            throw new BusinessException("请勾选数据！");
        }
        Arrays.stream(ids).forEach(this::del);
    }
}
