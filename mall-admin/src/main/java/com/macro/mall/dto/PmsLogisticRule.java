package com.macro.mall.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PmsLogisticRule {
    private List<PmsLogisticRule> brands = new ArrayList<>();
    private String ruleName;
    private String ruleType;

    public PmsLogisticRule(String ruleType, String ruleName) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
    }

    public PmsLogisticRule(List<PmsLogisticRule> brands, String ruleName, String ruleType) {
        this.brands = brands;
        this.ruleName = ruleName;
        this.ruleType = ruleType;
    }
}
