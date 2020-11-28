package com.duzhuo.wansystem.mapper.house;

import com.duzhuo.wansystem.dto.house.HouseCountDto;
import freemarker.cache.StrongCacheStorage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
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

    /**
     * 城市走势
     * @return
     */
    public List<HouseCountDto> statisticCity() {
        String i = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sql = "SELECT B1.id,b1.name,B2.LJ_HOUSE_COUNT AS num,TO_CHAR(B2.RECORD_DATE,'YYYY-MM-DD') AS dateStr FROM\n" +
                "(SELECT t2.id,t2.name FROM T_HOUSE_HOUSE_COUNT t1\n" +
                "LEFT JOIN T_HOUSE_CITY t2 ON t1.CITY_ID = t2.ID \n" +
                "WHERE t1.RECORD_DATE=to_date(?,'YYYY-MM-DD')\n" +
                "AND t1.LJ_HOUSE_COUNT IS NOT NULL ORDER BY t1.LJ_HOUSE_COUNT DESC\n" +
                ")b1\n" +
                "LEFT JOIN T_HOUSE_HOUSE_COUNT b2 ON b1.id = b2.city_id";
        List<Map<String, Object>> dataMap = jdbcTemplate.queryForList(sql, i);
        List<HouseCountDto> houseCountDtoList = new ArrayList<>();
        dataMap.forEach(m->{
            String cityName = m.get("name").toString();
            String num = m.get("num").toString();
            String date = m.get("dateStr").toString();
            Long id= Long.valueOf(m.get("id").toString());
            HouseCountDto houseCountDto = this.have(houseCountDtoList,cityName,id);
            houseCountDto.add(cityName,date,num);
            this.add(houseCountDtoList,houseCountDto);
        });
        return sort(houseCountDtoList);
    }

    /**
     *
     * @param houseCountDtoList
     * @param cityName
     * @return
     */
    private HouseCountDto have(List<HouseCountDto> houseCountDtoList,String cityName,Long id){
        for (HouseCountDto h:houseCountDtoList){
            if (h.getUrl().getName().equals(cityName)){
                return h;
            }
        }
        return new HouseCountDto(id,cityName);
    }

    private void add(List<HouseCountDto> houseCountDtoList,HouseCountDto houseCountDto){
        for (HouseCountDto h:houseCountDtoList){
            if (h.getUrl().getName().equals(houseCountDto.getUrl().getName())){
                return;
            }
        }
        houseCountDtoList.add(houseCountDto);
    }

    private List<HouseCountDto> sort(List<HouseCountDto> houseCountDtoList){
        houseCountDtoList.forEach(h->Collections.sort(h.getData()));
        return houseCountDtoList;
    }
}
