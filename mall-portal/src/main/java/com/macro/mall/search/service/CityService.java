package com.macro.mall.search.service;

import com.macro.mall.model.TDAreainfo;
import com.macro.mall.search.model.CityModel;

import java.util.List;

public interface CityService {
    CityModel listAllProvice();

    List<TDAreainfo> listAllCitys();
}
