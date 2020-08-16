package com.sfac.javaSpringBoot.modules.test.service;

import com.sfac.javaSpringBoot.modules.test.entity.Country;

public interface CountryService {
    Country getCountryByCountryId(int countryId);

    Country getCountryByCountryName(String countryName);

    Country mograteCountryByRedis(int countryId);
}
