package com.duzhuo.wansystem.entity.house;

import com.duzhuo.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: 万宏远
 * @email: 1434495271@qq.com
 * @date: 2020/8/18 12:03
 */
@Data
@ApiModel(value = "城市代码")
@Entity
@Table(name = "T_HOUSE_HOUSE_COUNT")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "T_BASE_SEQ", allocationSize = 1)
public class HouseCount extends BaseEntity{

    private static final long serialVersionUID = -2275557546024460427L;
    private Date recordDate;
    @ManyToOne
    @JoinColumn(name = "CITY_ID")
    private CityUrl cityUrl;

    @ApiModelProperty(value = "链家")
    private Integer ljHouseCount;
    @ApiModelProperty(value = "诸葛")
    private Integer zgHouseCount;

}
