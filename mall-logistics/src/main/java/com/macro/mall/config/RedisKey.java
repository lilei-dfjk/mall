package com.macro.mall.config;

public class RedisKey {
    private static final String LOGISTICS_RULE_TYPE_KEY = "logistics.rule.type.s%.s&";

    public static String getLogisticsRuleType(String logisType, String productType) {
        return String.format(LOGISTICS_RULE_TYPE_KEY, logisType, productType);
    }

}
