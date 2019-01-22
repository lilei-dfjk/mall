package com.macro.mall.portal.service.impl;

import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.cons.LogisticType;
import com.macro.mall.mapper.PmsProductLogisticRuleMapper;
import com.macro.mall.mapper.TLogicsRuleMapper;
import com.macro.mall.model.PmsProductLogisticRule;
import com.macro.mall.model.PmsProductLogisticRuleExample;
import com.macro.mall.model.TLogicsRule;
import com.macro.mall.model.TLogicsRuleExample;
import com.macro.mall.portal.constans.RedisKey;
import com.macro.mall.portal.service.PmsProductLogisticRuleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PmsProductLogisticRuleServiceImpl implements PmsProductLogisticRuleService {

    @Autowired
    private TLogicsRuleMapper ruleMapper;
    @Autowired
    private PmsProductLogisticRuleMapper productLogisticRuleMapper;

    @Cacheable(value = RedisKey.PORTAL_PRODUCT, key = RedisKey.PRODUCT_RULE + "'#productId'" + "'.#logisticType'")
    @Override
    public LogisticsRuleBean getLogisticRuleBean(long productId, LogisticType logisticType) {
        PmsProductLogisticRuleExample example = new PmsProductLogisticRuleExample();
        example.createCriteria().andProductIdEqualTo(productId).andLogisticTypeEqualTo((short) logisticType.getValue());
        List<PmsProductLogisticRule> pmsProductLogisticRules = productLogisticRuleMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(pmsProductLogisticRules)) {
            PmsProductLogisticRule pmsProductLogisticRule = pmsProductLogisticRules.get(0);
            TLogicsRuleExample tLogicsRuleExample = new TLogicsRuleExample();
            tLogicsRuleExample.createCriteria().andTypeEqualTo(pmsProductLogisticRule.getRuleType())
                    .andBrandTypeEqualTo(pmsProductLogisticRule.getRuleBrandType());
            List<TLogicsRule> tLogicsRules = ruleMapper.selectByExample(tLogicsRuleExample);
            if (CollectionUtils.isNotEmpty(tLogicsRules)) {
                TLogicsRule tLogicsRule = tLogicsRules.get(0);
                LogisticsRuleBean ruleBean = new LogisticsRuleBean();
                ruleBean.setBrandRuleType(tLogicsRule.getBrandType());
                ruleBean.setId(tLogicsRule.getId());
                ruleBean.setLogisType(tLogicsRule.getType());
                ruleBean.setMixNumberLimit(tLogicsRule.getMixNumberLimit());
                ruleBean.setMixPriceLimit(tLogicsRule.getMixPriceLimit());
                ruleBean.setMixWeightLimit(tLogicsRule.getMixWeightLimit());
                ruleBean.setNumberLimit(tLogicsRule.getNumberLimit());
                ruleBean.setWeightLimit(tLogicsRule.getWeightLimit());
                ruleBean.setPriceLimit(tLogicsRule.getPriceLimit());
                ruleBean.setMixTypeFlag(tLogicsRule.getMixTypeFlag() == 1 ? true : false);
                return ruleBean;
            }
        }
        return null;
    }
}
