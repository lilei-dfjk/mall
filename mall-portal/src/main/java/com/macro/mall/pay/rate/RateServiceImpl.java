package com.macro.mall.pay.rate;

import com.macro.mall.mapper.RateMapper;
import com.macro.mall.model.Rate;
import com.macro.mall.pay.Currency;
import com.macro.mall.pay.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    private RateMapper rateMapper;

    @Override
    public double getAuToCnyRate() {
        Rate rateByType = this.getRate(Currency.AUD.name(), Currency.CNY.name());
        return rateByType.getRate();
    }

    @Cacheable(value = RedisKey.PORTAL_RATE, key = RedisKey.CURRENT_RATE_AU_CN)
    @Override
    public Rate getRate(String currency, String baseCurrency) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("currency", currency);
        map.put("baseCurrency", baseCurrency);
        return rateMapper.getByCurrency(map);
    }

    @CacheEvict(value = RedisKey.PORTAL_RATE, key = RedisKey.CURRENT_RATE_AU_CN)
    @Override
    public void save(Rate rate) {
        rateMapper.insert(rate);
    }

    @CacheEvict(value = RedisKey.PORTAL_RATE, key = RedisKey.CURRENT_RATE_AU_CN)
    @Override
    public void update(Rate rate) {
        rateMapper.update(rate);
    }

}
