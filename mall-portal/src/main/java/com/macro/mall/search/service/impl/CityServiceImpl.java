package com.macro.mall.search.service.impl;

import com.macro.mall.mapper.SysAreasMapper;
import com.macro.mall.model.SysAreas;
import com.macro.mall.model.SysAreasExample;
import com.macro.mall.search.constans.RedisKey;
import com.macro.mall.search.model.CityModel;
import com.macro.mall.search.service.CityService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private SysAreasMapper sysAreas;

    //    @Cacheable(value = RedisKey.PORTAL_SEARCH, key = RedisKey.SEARCH_CITY_LIST)
    @Override
    public CityModel listAllProvice() {
//        TDAreainfoExample example = new TDAreainfoExample();
//        example.createCriteria().andArealevelEqualTo((byte) 1);
//        List<TDAreainfo> provinces = areainfoMapper.selectByExample(example);
//        List<TDAreainfo> countys = areainfoMapper.selectByExample(example);
//        List<TDAreainfo> citys = areainfoMapper.selectByExample(example);
//        Map<String, String> provinces_list = provinces.stream().collect(Collectors.toMap(province -> province.getId() + "", province -> province.getName()));
//        Map<String, String> citys_list = citys.stream().collect(Collectors.toMap(province -> province.getId() + "", province -> province.getName()));
//        Map<String, String> countys_list = countys.stream().collect(Collectors.toMap(province -> province.getId() + "", province -> province.getName()));
//        return new CityModel(provinces_list, citys_list, countys_list);
        return null;
    }

    @Cacheable(value = RedisKey.PORTAL_SEARCH, key = RedisKey.SEARCH_CITY_LIST)
    @Override
    public List<CityModel> listAllCitys() {
        List<SysAreas> sysAreas = this.sysAreas.selectByExample(new SysAreasExample());
        if (CollectionUtils.isNotEmpty(sysAreas)) {
            return sysAreas.stream().map(area -> new CityModel(Byte.parseByte(area.getLevelType()), Integer.parseInt(area.getId()), area.getShortName(), Integer.parseInt(area.getParentId()))).collect(Collectors.toList());
        }
        return null;
    }
}
