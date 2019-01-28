package com.macro.mall.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PmsLogisticRule {
    private List<PmsLogisticRule> brands;
    private short ruleName;
    private short ruleType;
}
