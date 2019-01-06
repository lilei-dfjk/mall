package com.macro.mall.pay.rate;

import com.macro.mall.model.Rate;

public interface RateService {

    double getAuToCnyRate();

    Rate getRate(String currency, String baseCurrency);

    void save(Rate rate);

    void update(Rate rate);
}
