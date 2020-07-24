package ${data.servicepackage};

import com.jxyunge.enums.ApiResultEnum;
import com.jxyunge.exception.ApiException;
import com.jxyunge.mybatis.dto.system.ApiResult;
import ${data.entityPackages};
import ${data.daopackage}.${data.entityName}Mapper;
import com.jxyunge.utils.ApiResultUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        throw new ApiException(ApiResultEnum.FAILED);
    }

    /**
     * ${data.module}--新增
     * @param ${data.lowEntityName}VO
     * @return
     */
    public ApiResult addData(${data.entityName} ${data.lowEntityName}VO) {
        this.check(${data.lowEntityName}VO);
        super.save(${data.lowEntityName}VO);
        return ApiResultUtil.success(${data.lowEntityName}VO);
    }


    /**
     * ${data.module}--修改
     * @param ${data.lowEntityName}VO
     * @return
     */
    public ApiResult edit(${data.entityName} ${data.lowEntityName}VO) {
        this.check(${data.lowEntityName}VO);
        ${data.entityName} ${data.lowEntityName} = super.getById(${data.lowEntityName}VO.getId());

        super.update(${data.lowEntityName});
        return ApiResultUtil.success(${data.lowEntityName});
    }

    /**
     * ${data.module}--删除
     * @param id
     * @return
     */
    public ApiResult del(Long id) {
        throw new ApiException(ApiResultEnum.FAILED);
    }
}
