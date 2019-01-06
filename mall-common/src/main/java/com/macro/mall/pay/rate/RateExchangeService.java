package com.macro.mall.pay.rate;

import com.macro.mall.model.Rate;

public interface RateExchangeService {

    Rate getRateByType(String currencyType, String baseCurrencyType);

    void update(Rate rate);

    void insert(Rate rate);

}
