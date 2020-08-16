package com.sfac.javaSpringBoot.modules.test.controller;

import com.sfac.javaSpringBoot.modules.test.entity.Country;
import com.sfac.javaSpringBoot.modules.test.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/country/{countryId}")
    public Country getCountryByCountryId(@PathVariable int countryId){
        return countryService.getCountryByCountryId(countryId);
    }



    @GetMapping("/country")
    public Country getCountryByCountryName(@RequestParam String countryName){
        return countryService.getCountryByCountryName(countryName);
    }

    @GetMapping("/redis/country/{countryId}")
    public Country mograteCountryByRedis(@PathVariable int countryId) {
        return countryService.mograteCountryByRedis(countryId);
    }
}
