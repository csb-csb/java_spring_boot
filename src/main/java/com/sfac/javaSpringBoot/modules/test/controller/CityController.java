package com.sfac.javaSpringBoot.modules.test.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.javaSpringBoot.modules.common.vo.Result;
import com.sfac.javaSpringBoot.modules.common.vo.SearchVo;
import com.sfac.javaSpringBoot.modules.test.entity.City;
import com.sfac.javaSpringBoot.modules.test.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/cities/{countryId}")
    public List<City> getCitesByCountryId(@PathVariable int countryId){
        return cityService.getCitesByCountryId(countryId);
    }
    /**
     * 127.0.0.1/api/cities/522 --- post
     * {"currentPage":"1","pageSize":"5"}
     */
    @PostMapping(value = "/cities", consumes = "application/json")
    public PageInfo<City> getCitiesBySearchVo(@RequestBody SearchVo searchVo) {
        return cityService.getCitiesBySearchVo(searchVo);
    }




    /**
     * 127.0.0.1/api/city ---- post
     * {"cityName":"test1","localCityName":"freeCity","countryId":"522"}
     */
    @PostMapping(value = "/city", consumes = "application/json")
    public Result<City> insertCity(@RequestBody City city) {
        return cityService.insertCity(city);
    }
    /**
     * 127.0.0.1/api/city ---- put
     * "cityId"="2259",cityName"="aaaaa"
     */
    @PutMapping(value = "/city",consumes ="application/x-www-form-urlencoded")
    public Result<City> updateCity(@ModelAttribute City city) {
        return cityService.updateCity(city);
    }


    /**
     * 127.0.0.1/api/city/2258 ---- delete
     */
    @DeleteMapping (value = "/city/{cityId}")
    public Result<Object> deleteCity(@PathVariable int cityId) {



        return cityService.deleteCity(cityId);
    }

}
