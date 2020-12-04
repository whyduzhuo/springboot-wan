package com.duzhuo.wansystem.entity.house;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/18 12:03
 */
@ApiModel(value = "城市代码")
@Entity
@Table(name = "T_HOUSE_HOUSE_COUNT")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class HouseCount extends BaseEntity{
    private Date recordDate;
    private CityUrl cityUrl;

    @ApiModelProperty(value = "链家")
    private Integer ljHouseCount;
    @ApiModelProperty(value = "诸葛")
    private Integer zgHouseCount;

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    public CityUrl getCityUrl() {
        return cityUrl;
    }

    public void setCityUrl(CityUrl cityUrl) {
        this.cityUrl = cityUrl;
    }

    public Integer getLjHouseCount() {
        return ljHouseCount;
    }

    public void setLjHouseCount(Integer ljHouseCount) {
        this.ljHouseCount = ljHouseCount;
    }

    public Integer getZgHouseCount() {
        return zgHouseCount;
    }

    public void setZgHouseCount(Integer zgHouseCount) {
        this.zgHouseCount = zgHouseCount;
    }
}
