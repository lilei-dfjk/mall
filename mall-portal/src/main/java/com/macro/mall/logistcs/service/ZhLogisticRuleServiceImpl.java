package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.cons.RedisKey;
import com.macro.mall.mapper.TLogicsRuleMapper;
import com.macro.mall.model.TLogicsRule;
import com.macro.mall.model.TLogicsRuleExample;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZhLogisticRuleServiceImpl implements ZhLogisticRuleService {
    @Autowired
    private TLogicsRuleMapper ruleMapper;

    @Cacheable(value = RedisKey.PORTAL_LOGISTICS, key = RedisKey.LOGISTICS_RULE + "'#logisticType'" + "'.#brandRuleType'")
    @Override
    public LogisticsRuleBean getLogisticsRulesByLogisType(Short logisticType, String brandRuleType) {
        TLogicsRuleExample example = new TLogicsRuleExample();
        example.createCriteria().andLogisTypeEqualTo(logisticType).andBrandTypeEqualTo(brandRuleType);
        List<TLogicsRule> tLogicsRules = ruleMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(tLogicsRules)) {
            TLogicsRule tLogicsRule = tLogicsRules.get(0);
            LogisticsRuleBean ruleBean = new LogisticsRuleBean();
            ruleBean.setBrandRuleType(tLogicsRule.getBrandType());
            ruleBean.setId(tLogicsRule.getId());
            ruleBean.setLogisType(tLogicsRule.getType());
            ruleBean.setMixNumberLimit(tLogicsRule.getMixNumberLimit());
            ruleBean.setMixPriceLimit(tLogicsRule.getMixPriceLimit());
            ruleBean.setMixWeightLimit(tLogicsRule.getMixWeightLimit());
            ruleBean.setNumberLimit(tLogicsRule.getNumberLimit());
            ruleBean.setWeightLimit(tLogicsRule.getWeightLimit());
            ruleBean.setPriceLimit(tLogicsRule.getPriceLimit());
            ruleBean.setMixTypeFlag(tLogicsRule.getMixTypeFlag() == 1 ? true : false);
            return ruleBean;
        }
        return null;
    }

    private int calcNumberRules(int number) {
        LogisticsRuleBean ruleBean = new LogisticsRuleBean();
        if (number > ruleBean.getNumberLimit()) {
            return ruleBean.getNumberLimit() - number;
        }
        return 0;
    }

    private double calcWeightRules(double weight) {
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
