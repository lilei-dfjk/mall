package com.macro.mall.service;

import com.macro.mall.dto.PmsLogisticRule;

import java.util.List;

public interface PmsLogisticRuleService {

    List<PmsLogisticRule> listRulesByLogisticType(short logisticType);
}
