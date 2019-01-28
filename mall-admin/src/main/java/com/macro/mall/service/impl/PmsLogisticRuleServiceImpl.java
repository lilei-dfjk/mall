package com.macro.mall.service.impl;

import com.macro.mall.mapper.TLogicsRuleMapper;
import com.macro.mall.model.TLogicsRule;
import com.macro.mall.model.TLogicsRuleExample;
import com.macro.mall.service.PmsLogisticRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsLogisticRuleServiceImpl implements PmsLogisticRuleService {
    @Autowired
    private TLogicsRuleMapper ruleMapper;

    @Override
    public void listRulesByLogisticType(short logisticType) {
        TLogicsRuleExample example = new TLogicsRuleExample();
        List<TLogicsRule> tLogicsRules = ruleMapper.selectByExample(example);
    }
}
