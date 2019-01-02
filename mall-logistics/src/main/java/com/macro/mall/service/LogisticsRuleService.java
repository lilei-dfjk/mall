package com.macro.mall.service;

import com.macro.mall.bean.LogisticsMixRuleBean;
import com.macro.mall.bean.LogisticsRuleBean;

import java.util.List;

public interface LogisticsRuleService {
    LogisticsRuleBean getLogisticsRulesByLogisType(String logisType, String productType);
    List<LogisticsRuleBean> getMixLogisticsRulesByLogisType(String logisType, String productType);

    LogisticsMixRuleBean getLogisticsRulesByLogisType(String logisticsType, String productType, String productType1);
}
