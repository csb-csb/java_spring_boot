package com.sfac.javaSpringBoot.modules.test.service.impl;

import com.sfac.javaSpringBoot.modules.test.dao.CountryDao;
import com.sfac.javaSpringBoot.modules.test.entity.Country;
import com.sfac.javaSpringBoot.modules.test.service.CountryService;
import com.sfac.javaSpringBoot.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryServiceImpl implements CountryService {

@Autowired
private CountryDao countryDao;
@Autowired
private RedisUtils redisUtils;

    @Override
    public Country getCountryByCountryId(int countryId) {
        return countryDao.getCountryByCountryId(countryId);
    }

    @Override
    public Country getCountryByCountryName(String countryName) {
        return countryDao.getCountryByCountryName(countryName);
    }

    @Override
   // @Transactional
    @Transactional(noRollbackFor = ArithmeticException.class)
    public Country mograteCountryByRedis(int countryId) {
      Country country= countryDao.getCountryByCountryId(countryId);
      String countryKey=String.format("country%d",countryId);
      redisUtils.set(countryKey,country);
      return (Country) redisUtils.get(countryKey);
    }
}
