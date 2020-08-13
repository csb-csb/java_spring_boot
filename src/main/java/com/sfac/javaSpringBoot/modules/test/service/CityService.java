package com.sfac.javaSpringBoot.modules.test.service;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.City;

import java.util.List;

public interface CityService {
    List<City> getCitesByCountryId(int countryId);

    PageInfo<City> getCitiesBySearchVo(int countryId, SearchVo searchVo);

    Result<City> insertCity(City city);

    PageInfo<City> getCitiesBySearchVo(SearchVo searchVo);

    Result<City> updateCity(City city);

    Result<Object> deleteCity(int cityId);
}
