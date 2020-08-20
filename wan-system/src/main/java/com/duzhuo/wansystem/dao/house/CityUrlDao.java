package  com.duzhuo.wansystem.dao.house;

import com.duzhuo.common.core.BaseDao;
import  com.duzhuo.wansystem.entity.house.CityUrl;

import java.util.List;


/**
 * 城市代码--Mapper
 * @author: 万宏远
 * @date: 2020/08/18 10:52:53
 */
public interface CityUrlDao extends BaseDao<CityUrl,Long> {

    CityUrl findByName(String name);

    List<CityUrl> findByLjUrlIsNotNull();
}

