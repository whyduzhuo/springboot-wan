package  com.duzhuo.wansystem.dao.house;

import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.house.ExcelDemo;
import org.springframework.transaction.annotation.Transactional;


/**
 * ExcelDemo--Mapper
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2020/12/16 18:04:40
 */
@Transactional(rollbackFor = Exception.class)
public interface ExcelDemoDao extends BaseDao<ExcelDemo,Long> {
}

