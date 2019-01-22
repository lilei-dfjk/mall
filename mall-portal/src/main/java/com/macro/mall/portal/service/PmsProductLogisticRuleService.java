package com.macro.mall.portal.service;

import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.cons.LogisticType;

public interface PmsProductLogisticRuleService {
    LogisticsRuleBean getLogisticRuleBean(long productId, LogisticType logisticType);
}
