package com.macro.mall.service.impl;

import com.macro.mall.bean.LogisticsMixRuleBean;
import com.macro.mall.bean.LogisticsRuleBean;
import com.macro.mall.config.RedisKey;
import com.macro.mall.service.LogisticsRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogisticsRuleServiceImpl implements LogisticsRuleService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public LogisticsRuleBean getLogisticsRulesByLogisType(String logisType, String productType) {
        LogisticsRuleBean ruleBean = (LogisticsRuleBean) redisTemplate.opsForValue().get(RedisKey.getLogisticsRuleType(logisType, productType));
        return ruleBean;
    }

    @Override
    public List<LogisticsRuleBean> getMixLogisticsRulesByLogisType(String logisType, String productType) {
        return null;
    }

    @Override
    public LogisticsMixRuleBean getLogisticsRulesByLogisType(String logisticsType, String productType, String productType1) {
        return null;
    }

    private int calcNumberRules(int number) {
        LogisticsRuleBean ruleBean = new LogisticsRuleBean();
        if (number > ruleBean.getNumberLimit()) {
            return ruleBean.getNumberLimit() - number;
        }
        return 0;
    }

    private int calcWeightRules(int weight) {
        LogisticsRuleBean ruleBean = new LogisticsRuleBean();
        if (weight > ruleBean.getWeightLimit()) {
            return ruleBean.getWeightLimit() - weight;
        }
        return 0;
    }

    private double calcPriceRules(double price) {
        LogisticsRuleBean ruleBean = new LogisticsRuleBean();
        if (price > ruleBean.getPriceLimit()) {
            return ruleBean.getWeightLimit() - price;
        }
        return 0;
    }

    private List createLogicsOrder(int orderId, int number, double price, int weight, List list) {
        int number2 = calcNumberRules(number);
        int weight2 = calcNumberRules(weight);
        double price2 = calcPriceRules(price);
        if (number2 > 0 || weight2 > 0 || price2 > 0) {
            createLogicsOrder(orderId, number2, price2, weight2, list);
        }
        list.add(number2);
        return list;
    }

}
