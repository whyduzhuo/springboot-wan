package  com.duzhuo.wansystem.dao.house;

import com.duzhuo.common.core.BaseDao;
import com.duzhuo.wansystem.entity.house.CityUrl;
import  com.duzhuo.wansystem.entity.house.HouseCount;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 二手房统计--Mapper
 * @author: 万宏远
 * @date: 2020/08/18 12:51:09
 */
public interface HouseCountDao extends BaseDao<HouseCount,Long> {

    /**
     * 某日谋城市的数据
     * @param cityUrl
     * @param recondDate
     * @return
     */
    HouseCount findByCityUrlAndRecordDate(CityUrl cityUrl,Date recondDate);

    /**
     * 查询全国二手房挂牌量统计
     * @return
     */
    @Query(value = "SELECT TO_CHAR(t1.RECORD_DATE,'YYYY-MM-DD') AS d ,\n" +
            "sum(t1.LJ_HOUSE_COUNT) AS c\n" +
            "FROM T_HOUSE_HOUSE_COUNT t1 \n" +
            "GROUP BY TO_CHAR(t1.RECORD_DATE,'YYYY-MM-DD')\n" +
            "HAVING sum(t1.LJ_HOUSE_COUNT) >0 \n" +
            "ORDER BY TO_CHAR(t1.RECORD_DATE,'YYYY-MM-DD') DESC ",nativeQuery = true)
    List<Map<String,Object>> statisticContry();
}

