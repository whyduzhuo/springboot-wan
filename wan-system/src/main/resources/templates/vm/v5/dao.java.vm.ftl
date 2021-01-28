package ${data.daopackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${data.entityPackages};


/**
 * ${data.module}--Mapper
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */
public interface ${data.entityName}Mapper extends BaseMapper<${data.entityName}> {

    /**
     *
     * @param page
     * @param searchParams
     * @return
     */
    IPage<${data.entityName}Dto> pageDto(IPage<${data.entityName}Dto> page, @Param("p") Map<String, Object> searchParams);

    /**
    *
    * @param searchParams
    * @return
    */
    List<${data.entityName}Dto> pageDto(@Param("p") Map<String, Object> searchParams);

    /**
    *
    * @param id
    * @return
    */
    ${data.entityName}Dto findDtoById(@Param("id") Serializable id);
}
