package com.duzhuo.wansystem.mapper.house;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: wanhy
 * @date: 2020/8/20 14:13
 */
@Repository
public class HouseCountMapper {
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据日期排序
     * @param date
     * @return
     */
    public List<Map<String,Object>> statisticDate(String date) {
        String sql = "SELECT t1.LJ_HOUSE_COUNT,t2.NAME FROM T_HOUSE_HOUSE_COUNT t1 LEFT JOIN T_HOUSE_CITY t2 on t1.CITY_ID = t2.ID WHERE t1.RECORD_DATE=to_date(?,'YYYY-MM-DD') AND t1.LJ_HOUSE_COUNT is not NULL ORDER BY t1.LJ_HOUSE_COUNT ASC";
        return jdbcTemplate.queryForList(sql,date);
    }
}
