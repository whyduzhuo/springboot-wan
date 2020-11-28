package  com.duzhuo.wansystem.service.house;

import com.duzhuo.common.core.BaseService;
import com.duzhuo.common.core.Message;
import com.duzhuo.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import com.duzhuo.wansystem.dao.house.CityUrlDao;
import com.duzhuo.wansystem.entity.house.CityUrl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 城市代码--Service
 * @author: 万宏远
 * @date: 2020/08/18 10:51:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CityUrlService extends BaseService<CityUrl, Long> {

    @Resource
    private CityUrlDao cityUrlDao;

    @Resource
    public void setBaseDao(CityUrlDao cityUrlDao){
        super.setBaseDao(cityUrlDao);
    }


    /**
     * 链家--路径--新增
     * @return
     */
    public Message addLjData(CityUrl cityUrlVO){
        if (StringUtils.isBlank(cityUrlVO.getName())){
            throw new ServiceException("城市名称不能为空");
        }
        CityUrl cityUrl = cityUrlDao.findByName(cityUrlVO.getName());
        if (cityUrl==null){
            super.save(cityUrlVO);
        }else {
            cityUrl.setLjUrl(cityUrlVO.getLjUrl());
            super.save(cityUrl);
        }
        return Message.success("添加成功！");
    }

    /**
     * 添加诸葛地址
     * @param cityUrlVO
     * @return
     */
    public Message addZgData(CityUrl cityUrlVO){
        if (StringUtils.isBlank(cityUrlVO.getName())){
            throw new ServiceException("城市名称不能为空");
        }
        if (StringUtils.isBlank(cityUrlVO.getZgUrl())){
            throw new ServiceException("地址不能为空！");
        }
        CityUrl cityUrl = cityUrlDao.findByName(cityUrlVO.getName());
        if (cityUrl==null){
            super.save(cityUrlVO);
        }else {
            cityUrl.setZgUrl(cityUrlVO.getZgUrl());
            super.save(cityUrl);
        }
        return Message.success("添加成功！");
    }



    /**
     * 城市代码--删除
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

    public List<CityUrl> findByLjUrlIsNotNull() {
        return cityUrlDao.findByLjUrlIsNotNull();
    }

    public CityUrl findName(String cityName) {
        return cityUrlDao.findByName(cityName);
    }
}
