package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsRuleBean;

public interface ZhLogisticRuleService {
    LogisticsRuleBean getLogisticsRulesByLogisType(String brandRuleType);
}
