package  com.duzhuo.wansystem.dao.house;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.house.CityUrl;
import  com.duzhuo.wansystem.entity.house.HouseCount;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 二手房统计--Mapper
 * @author: 万宏远
 * @date: 2020/08/18 12:51:09
 */
public interface HouseCountDao extends BaseDao<HouseCount,Long> {

    /**
     *
     * @param cityUrl
     * @param recondDate
     * @return
     */
    HouseCount findByCityUrlAndRecordDate(CityUrl cityUrl,Date recondDate);
}

