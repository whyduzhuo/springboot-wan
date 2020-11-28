package  com.duzhuo.wansystem.service.house;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.thread.ThreadPoolService;
import com.duzhuo.common.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import com.duzhuo.wansystem.dao.house.HouseCountDao;
import com.duzhuo.wansystem.dto.house.HouseCountDto;
import com.duzhuo.wansystem.entity.house.CityUrl;
import com.duzhuo.wansystem.entity.house.HouseCount;
import com.duzhuo.wansystem.mapper.house.HouseCountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 二手房统计--Service
 * @author: 万宏远
 * @date: 2020/08/18 12:51:09
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class HouseCountService extends BaseService<HouseCount, Long>{

    @Resource
    private HouseCountDao houseCountDao;
    @Resource
    private CityUrlService cityUrlService;
    @Resource
    private HouseCountMapper houseCountMapper;
    @Resource
    private ThreadPoolService threadPoolService;

    @Resource
    public void setBaseDao(HouseCountDao houseCountDao){
        super.setBaseDao(houseCountDao);
    }

    /**
     * 二手房统计--数据校验
     * @param houseCountVO
     */
    private void check(HouseCount houseCountVO){
        if (houseCountVO.getCityUrl()==null){
            throw new ServiceException("city为空！");
        }
        if (houseCountVO.getRecordDate()==null){
            throw new ServiceException("日期为空！");
        }
    }

    /**
     * 二手房统计--新增
     * @param houseCountVO
     * @return
     */
    public Message addData(HouseCount houseCountVO) {
        this.check(houseCountVO);
        HouseCount houseCount = houseCountDao.findByCityUrlAndRecordDate(houseCountVO.getCityUrl(),houseCountVO.getRecordDate());
        if (houseCount==null){
            houseCount = new HouseCount();
        }
        houseCount.setCityUrl(houseCountVO.getCityUrl());
        houseCount.setRecordDate(houseCountVO.getRecordDate());
        houseCount.setLjHouseCount(houseCountVO.getLjHouseCount());
        super.save(houseCount);
        return Message.success("添加成功!");
    }


    /**
     * 二手房统计--修改
     * @param houseCountVO
     * @return
     */
    public Message edit(HouseCount houseCountVO) {
        this.check(houseCountVO);
        HouseCount houseCount = super.find(houseCountVO.getId());
        super.update(houseCount);
        return Message.success("修改成功!");
    }

    /**
     * 二手房统计--删除
     * @param id
     * @return
     */
    public Message del(Long id) {
        if (id==null){
            throw new ServiceException("请选择数据！");
        }
        super.delete(id);
        return Message.success("删除成功！");
    }

    /**
     *
     * @return
     */
    public Message addBatch(){
        List<CityUrl> cityUrlList = cityUrlService.findByLjUrlIsNotNull();
        for (CityUrl cityUrl:cityUrlList) {
            threadPoolService.execute(()-> {
                try {
                    HouseCount houseCount = new HouseCount();
                    houseCount.setCityUrl(cityUrl);
                    houseCount.setLjHouseCount(getLj(cityUrl));
                    houseCount.setRecordDate(formateDate(new Date(), "yyyy-MM-dd"));
                    addData(houseCount);

                }catch (Exception e){
                    log.error(e.getMessage());
                }
            });
        }
        return Message.success("大约10秒后刷新页面");
    }

    /**
     * 查询链家的二手房挂牌量
     * @param cityUrl
     * @return
     */
    public Integer getLj(CityUrl cityUrl){
        String ljUrl = cityUrl.getLjUrl();
        if (StringUtils.isBlank(ljUrl)){
            return null;
        }
        String pageCode = HttpUtils.get(ljUrl);
        String startStr = "共找到<span>";
        int start =pageCode.indexOf(startStr)+startStr.length();
        String endStr = "</span>套";
        int end = pageCode.indexOf(endStr);
        String b = StringUtils.deleteWhitespace(pageCode.substring(start,end));
        int num = Integer.valueOf(b);
        return num;
    }

    /**
     * 爬取诸葛房源数
     * @param cityUrl
     * @return
     */
    public Integer getZg(CityUrl cityUrl){
        String url =  cityUrl.getZgUrl();
        if (StringUtils.isBlank(url)){
            return null;
        }
        String pageCode = HttpUtils.get(url);
        //<div class="list-converge-box">
        //                    为您从 <b>17890000</b> 套二手房源中，聚合出
        //                    <strong>314864</strong>
        //                    套有效二手房源
        //                </div>
        String startStr = "为您从 <b>";
        int start =pageCode.indexOf(startStr)+startStr.length();
        String endStr = "</b> 套二手房源中";
        int end = pageCode.indexOf(endStr);
        String b = StringUtils.deleteWhitespace(pageCode.substring(start,end));
        int num = Integer.valueOf(b);
        return num;
    }

    /**
     *
     * @param formate
     * @return
     */
    public Date formateDate(Date date,String formate) throws ParseException {
        String res = new SimpleDateFormat(formate).format(date);
        return new SimpleDateFormat(formate).parse(res);
    }

    /**
     * 日期统计
     * @param date
     * @return
     */
    public List<Map<String,Object>> statisticDate(String date) {
        return houseCountMapper.statisticDate(date);
    }

    /**
     * 城市走势
     * @return
     */
    public List<HouseCountDto> statisticCity() {
        return houseCountMapper.statisticCity();
    }

    /**
     * 全国二手房挂牌量走势
     * @return
     */
    public List<Map<String,Object>> statisticContry(){
        return houseCountDao.statisticContry();
    }
}
