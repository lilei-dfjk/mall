package com.macro.mall.service.impl;

import com.macro.mall.bean.*;
import com.macro.mall.service.LogisticsRuleService;
import com.macro.mall.service.LogisticsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsRuleService ruleService;

    @Override
    public void createLogisticsOrder(OrderBean orderBean) {
        List<LogisticsOrderBean> orders = new ArrayList<>();
        List<ProductItem> productItemList = orderBean.getProductItemList();
        if (!CollectionUtils.isEmpty(productItemList)) {
            // 第一类商品生成一个订单，如果不满足条件则生成多个订单，
            // 然后判断第二个商品，是否能够混装，然后以此类推
            productItemList.stream().forEach(productItem -> {
                createLogisticsOrder(orders, productItem, orderBean.getLogisticsType());
            });
        }
    }

    private List<LogisticsOrderBean> createLogisticsOrder(List<LogisticsOrderBean> logisticsOrderBeans, ProductItem productItem, String logisticsType) {
        LogisticsRuleBean logisticsRules = ruleService.getLogisticsRulesByLogisType(logisticsType, productItem.getProductType());
        LogisticsOrderBean orderBean = new LogisticsOrderBean();
        ProductItem item = new ProductItem();
        boolean mixFlag = false;
        if (CollectionUtils.isNotEmpty(logisticsOrderBeans)) {
            for (int i = 0; i < logisticsOrderBeans.size(); i++) {
                LogisticsOrderBean logisticsOrderBean = logisticsOrderBeans.get(i);
                if (!logisticsOrderBean.isFull() && logisticsRules.getMixs().contains(productItem.getProductType())) {
                    LogisticsMixRuleBean mixRules = ruleService.getLogisticsRulesByLogisType(logisticsType, productItem.getProductType(), productItem.getProductType());
                    LogisticsRuleBean ruleBean = mixRules.getMixs().get(productItem.getProductType());
                    if (compitableMix(ruleBean, productItem) && compitableMix(mixRules.getLogisticsRuleBean(), productItem)) {
                        BeanUtils.copyProperties(productItem, item);
                        logisticsOrderBean.getProductItemList().add(item);
                        logisticsOrderBean.setTotalNumber(logisticsOrderBean.getTotalNumber() + item.getAmount());
                        logisticsOrderBean.setTotalPrice(logisticsOrderBean.getTotalPrice() + item.getPrice());
                        logisticsOrderBean.setTotalWeight(logisticsOrderBean.getTotalWeight() + item.getWeight());
                        if (item.getAmount() > logisticsRules.getNumberLimit() && item.getWeight() > logisticsRules.getWeightLimit() && item.getPrice() > productItem.getPrice()) {
                            orderBean.setFull(true);
                        } else {
                            orderBean.setFull(false);
                        }
                        productItem.setPrice(0);
                        productItem.setWeight(0);
                        productItem.setAmount(0);
                        mixFlag = true;
                        break;
                    }
                }
            }
        }
        if (!mixFlag) {
            int amount = productItem.getAmount();
            int weight = productItem.getWeight();
            double price = productItem.getPrice();
            if (amount > logisticsRules.getNumberLimit() && weight > logisticsRules.getWeightLimit() && price > productItem.getPrice()) {
                orderBean.setFull(true);
            } else {
                orderBean.setFull(false);
            }
            if (productItem.getAmount() > logisticsRules.getNumberLimit()) {
                productItem.setAmount(productItem.getAmount() - logisticsRules.getNumberLimit());
                item.setAmount(logisticsRules.getNumberLimit());
            } else {
                productItem.setAmount(productItem.getAmount());
            }
            if (productItem.getWeight() > logisticsRules.getWeightLimit()) {
                productItem.setAmount(productItem.getWeight() - logisticsRules.getWeightLimit());
                item.setWeight(logisticsRules.getWeightLimit());
            } else {
                productItem.setWeight(productItem.getWeight());
            }
            if (productItem.getPrice() > logisticsRules.getPriceLimit()) {
                productItem.setPrice(productItem.getPrice() - logisticsRules.getPriceLimit());
                item.setPrice(logisticsRules.getPriceLimit());
            } else {
                productItem.setPrice(productItem.getPrice());
            }
            orderBean.getProductItemList().add(item);
            orderBean.getProductItemList().add(item);
            orderBean.setTotalNumber(orderBean.getTotalNumber() + item.getAmount());
            orderBean.setTotalPrice(orderBean.getTotalPrice() + item.getPrice());
            orderBean.setTotalWeight(orderBean.getTotalWeight() + item.getWeight());
            logisticsOrderBeans.add(orderBean);
        }

        if (productItem.getPrice() == 0 && productItem.getWeight() == 0 && productItem.getAmount() == 0) {
            return logisticsOrderBeans;
        } else {
            createLogisticsOrder(logisticsOrderBeans, productItem, logisticsType);
        }
        return null;
    }

    private boolean compitableMix(LogisticsRuleBean ruleBean, ProductItem item) {
        if (null == ruleBean) {
            return false;
        }
        if (null != ruleBean.getPriceLimit() && ruleBean.getPriceLimit() > item.getPrice()) {
            return false;
        }
        if (null != ruleBean.getWeightLimit() && ruleBean.getWeightLimit() > item.getWeight()) {
            return false;
        }
        if (null != ruleBean.getNumberLimit() && ruleBean.getNumberLimit() > item.getAmount()) {
            return false;
        }
        return true;
    }
}
