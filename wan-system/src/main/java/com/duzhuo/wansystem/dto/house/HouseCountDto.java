package com.duzhuo.wansystem.dto.house;

import com.duzhuo.wansystem.entity.house.CityUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wanhy
 * @date: 2020/8/22 12:44
 */

public class HouseCountDto {
    private CityUrl url;
    private List<TemDto> data = new ArrayList<>();

    public HouseCountDto() {
    }

    public HouseCountDto(Long id,String cityName) {
        CityUrl cityUrl = new CityUrl();
        cityUrl.setName(cityName);
        cityUrl.setId(id);
        this.url = cityUrl;
    }


    public HouseCountDto(CityUrl url) {
        this.url = url;
    }

    public CityUrl getUrl() {
        return url;
    }

    public void setUrl(CityUrl url) {
        this.url = url;
    }

    public List<TemDto> getData() {
        return data;
    }

    public void setData(List<TemDto> data) {
        this.data = data;
    }

    public boolean add(String city,String date,String num){
        if (!url.getName().equals(city)){
            return false;
        }
        TemDto temDto = new TemDto(date,Integer.valueOf(num));
        this.data.add(temDto);
        return true;
    }
}
