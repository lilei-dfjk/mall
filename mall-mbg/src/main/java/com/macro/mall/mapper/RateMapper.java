package com.macro.mall.mapper;

import com.macro.mall.model.Rate;

import java.util.Map;

public interface RateMapper {
    Rate getByCurrency(Map<String, String> map);

    void insert(Rate rate);

    void update(Rate rate);
}