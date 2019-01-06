package com.macro.mall.pay.rate;

import com.macro.mall.mapper.RateMapper;
import com.macro.mall.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class RateExchangeServiceImpl implements RateExchangeService {
    @Autowired
    private RateMapper rateMapper;

    @Override
    public double getRateByType(String currencyType, String baseCurrencyType) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("currency", currencyType);
        map.put("baseCurrency", baseCurrencyType);
        return rateMapper.getByCurrency(map);
    }

    @Override
    public void update(Rate rate) {
        rateMapper.update(rate);
    }

    @Override
    public void insert(Rate rate) {
        rateMapper.insert(rate);
    }
}
