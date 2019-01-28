package com.macro.mall.service.impl;

import com.macro.mall.dto.PmsLogisticRule;
import com.macro.mall.mapper.TLogicsRuleMapper;
import com.macro.mall.model.TLogicsRule;
import com.macro.mall.model.TLogicsRuleExample;
import com.macro.mall.service.PmsLogisticRuleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PmsLogisticRuleServiceImpl implements PmsLogisticRuleService {
    @Autowired
    private TLogicsRuleMapper ruleMapper;

    @Override
    public List<PmsLogisticRule> listRulesByLogisticType(short logisticType) {
        TLogicsRuleExample example = new TLogicsRuleExample();
        example.createCriteria().andLogisTypeEqualTo(logisticType);
        List<TLogicsRule> tLogicsRules = ruleMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(tLogicsRules)) {
            Map<String, String> ruleMap = new HashMap<>();
            Map<String, List<PmsLogisticRule>> listMap = new HashMap<>();
            tLogicsRules.stream().forEach(tLogicsRule -> {
                ruleMap.put(tLogicsRule.getType(), tLogicsRule.getName());
                if (listMap.containsKey(tLogicsRule.getType())) {
                    listMap.get(tLogicsRule.getType()).add(new PmsLogisticRule(tLogicsRule.getBrandType(), tLogicsRule.getBrandName()));
                } else {
                    List<PmsLogisticRule> list = new ArrayList<>();
                    list.add(new PmsLogisticRule(tLogicsRule.getBrandType(), tLogicsRule.getBrandName()));
                    listMap.put(tLogicsRule.getType(), list);
                }
            });
            return listMap.entrySet().stream().map(enty -> new PmsLogisticRule(enty.getValue(), ruleMap.get(enty.getKey()), enty.getKey())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
