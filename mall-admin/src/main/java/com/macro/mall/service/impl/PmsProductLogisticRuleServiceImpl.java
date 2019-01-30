package com.macro.mall.service.impl;

import com.macro.mall.mapper.PmsProductLogisticRuleMapper;
import com.macro.mall.model.PmsProductLogisticRule;
import com.macro.mall.model.PmsProductLogisticRuleExample;
import com.macro.mall.service.PmsProductLogisticRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductLogisticRuleServiceImpl implements PmsProductLogisticRuleService {

    @Autowired
    private PmsProductLogisticRuleMapper productLogisticRuleMapper;

    @Override
    public List listAllRules(Long productId) {
        PmsProductLogisticRuleExample example = new PmsProductLogisticRuleExample();
        example.createCriteria().andProductIdEqualTo(productId);
        List<PmsProductLogisticRule> pmsProductLogisticRules = productLogisticRuleMapper.selectByExample(example);
        return pmsProductLogisticRules;
    }

    @Override
    public void listRulesByLogisticType(String logisticType) {

    }
}
