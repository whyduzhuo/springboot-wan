package com.duzhuo.wansystem.entity.house;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/18 10:30
 */
@ApiModel(value = "城市代码")
@Entity
@Table(name = "T_HOUSE_CITY")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class CityUrl extends BaseEntity {

    @ApiModelProperty(value = "链家地址")
    private String ljUrl;
    @ApiModelProperty(value = "诸葛找房地址")
    private String zgUrl;
    private String name;

    private List<HouseCount> houseCountList;

    public String getLjUrl() {
        return ljUrl;
    }

    public void setLjUrl(String ljUrl) {
        this.ljUrl = ljUrl;
    }

    public String getZgUrl() {
        return zgUrl;
    }

    public void setZgUrl(String zgUrl) {
        this.zgUrl = zgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public List<HouseCount> getHouseCountList() {
        return houseCountList;
    }

    public void setHouseCountList(List<HouseCount> houseCountList) {
        this.houseCountList = houseCountList;
    }
}
