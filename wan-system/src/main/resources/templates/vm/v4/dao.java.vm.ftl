package ${data.daopackage};

import com.duzhuo.common.core.BaseDao;
import ${data.entityPackages};


/**
 * ${data.module}--Mapper
 * @email: ${data.email}
 * @author: ${data.author}
 * @date: ${data.createDateStr}
 */
@Transactional(rollbackFor = Exception.class)
public interface ${data.entityName}Dao extends BaseDao<${data.entityName},Long> {
}

