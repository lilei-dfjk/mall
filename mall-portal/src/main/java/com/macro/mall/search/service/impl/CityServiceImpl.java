package com.macro.mall.search.service.impl;

import com.macro.mall.mapper.TDAreainfoMapper;
import com.macro.mall.model.TDAreainfo;
import com.macro.mall.model.TDAreainfoExample;
import com.macro.mall.search.constans.RedisKey;
import com.macro.mall.search.model.CityModel;
import com.macro.mall.search.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private TDAreainfoMapper areainfoMapper;

    @Cacheable(value = RedisKey.PORTAL_SEARCH, key = RedisKey.SEARCH_CITY_LIST)
    @Override
    public CityModel listAllProvice() {
        TDAreainfoExample example = new TDAreainfoExample();
        example.createCriteria().andArealevelEqualTo((byte) 1);
        List<TDAreainfo> provinces = areainfoMapper.selectByExample(example);
        List<TDAreainfo> citys = areainfoMapper.selectByExample(example);
        List<TDAreainfo> countys = areainfoMapper.selectByExample(example);
        Map<String, String> provinces_list = provinces.stream().collect(Collectors.toMap(province -> province.getId() + "", province -> province.getName()));
        Map<String, String> citys_list = citys.stream().collect(Collectors.toMap(province -> province.getId() + "", province -> province.getName()));
        Map<String, String> countys_list = countys.stream().collect(Collectors.toMap(province -> province.getId() + "", province -> province.getName()));
        return new CityModel(provinces_list, citys_list, countys_list);
    }
}
