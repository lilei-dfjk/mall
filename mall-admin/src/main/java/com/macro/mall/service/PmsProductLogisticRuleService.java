package com.macro.mall.service;

import java.util.List;

public interface PmsProductLogisticRuleService {
    List listAllRules(Long productId);

    void listRulesByLogisticType(String logisticType);
}
