package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsRuleBean;

import java.util.List;

public class ZhLogisticRuleServiceImpl implements ZhLogisticRuleService{

    @Override
    public LogisticsRuleBean getLogisticsRulesByLogisType(String brandRuleType) {
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
