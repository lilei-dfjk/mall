package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZhLogisticServiceImpl implements ZhLogisticService {

    @Autowired
    private ZhLogisticRuleService ruleService;

    @Override
    public void getLogisticOrders(OrderBean orderBean) {
        List<ProductItem> productItemList = orderBean.getProductItemList();
        if (CollectionUtils.isNotEmpty(productItemList)) {
            // 1. 分类 尽量同一类放在一个包裹里
            List<String> collect = productItemList.stream().map(item -> item.getRuleType()).collect(Collectors.toList());
            Map<String, List<ProductItem>> listMap = collect.stream().collect(Collectors.toMap(type -> type, type -> getListByType(type, productItemList)));
            // 2. 每一类创建一个订单
            List<LogisticsOrderBean> logisticsOrderBeans = new ArrayList<>();
            listMap.entrySet().stream().forEach(entry -> logisticsOrderBeans.addAll(createLogisticOrder(entry.getKey(), entry.getValue())));
            // 3. 合并订单
            combineOrder(logisticsOrderBeans);
        }
    }

    private List<LogisticsOrderBean> combineOrder(List<LogisticsOrderBean> logisticsOrderBeans) {
        List<LogisticsOrderBean> lists = new ArrayList<>();
        logisticsOrderBeans.stream().forEach(orderBean -> {
            orderBean.getProductItemList().stream().forEach(productItem -> createLogisticsOrder(lists, productItem));
        });
        return null;
    }

    private List<ProductItem> getListByType(String type, List<ProductItem> items) {
        return items.stream().filter(item -> item.getRuleType().equals(type)).collect(Collectors.toList());
    }

    private List<LogisticsOrderBean> createLogisticOrder(String ruleType, List<ProductItem> items) {
        List<LogisticsOrderBean> orders = new ArrayList<>();
        if (!CollectionUtils.isEmpty(items)) {
            // 第一类商品生成一个订单，如果不满足条件则生成多个订单，
            // 然后判断第二个商品，是否能够混装，然后以此类推
            items.stream().forEach(productItem -> {
                createLogisticsOrder(orders, productItem);
            });
        }
        return new ArrayList<>();
    }

    private List<LogisticsOrderBean> createLogisticsOrder(List<LogisticsOrderBean> logisticsOrderBeans, ProductItem productItem) {
        if (null == productItem.getRuleBean()) {
            productItem.setRuleBean(ruleService.getLogisticsRulesByLogisType(productItem.getRuleBrandType()));
        }
        LogisticsRuleBean logisticsRules = productItem.getRuleBean();
        LogisticsOrderBean orderBean = new LogisticsOrderBean();
        ProductItem item = new ProductItem();
        boolean createFlag = false;
        if (CollectionUtils.isNotEmpty(logisticsOrderBeans)) {
            for (int i = 0; i < logisticsOrderBeans.size(); i++) {
                LogisticsOrderBean logisticsOrderBean = logisticsOrderBeans.get(i);
                if (!logisticsOrderBean.isFull()) {
                    List<ProductItem> productItemList = logisticsOrderBean.getProductItemList();
                    for (int j = 0; j < productItemList.size(); j++) {
                        ProductItem productItem1 = productItemList.get(j);
                        LogisticsRuleBean ruleBean = productItem1.getRuleBean();
                        if (!ruleBean.isMixTypeFlag() || !logisticsRules.isMixTypeFlag() || !ruleBean.getLogisType().equals(productItem1.getRuleType())) {
                            continue;
                        }
                        // 满足混装需求
                        // 1. 先获取自己的混装需求，然后获取对方混装需求，取最小 最后判断总订单是否满足
                        if (compitableMix(ruleBean, productItem) && compitableMix(logisticsRules, productItem)) {
                            List<ProductItem> lists = new ArrayList<>();
                            lists.addAll(productItemList);
                            lists.add(productItem);
                            if (totalCompitableMix(lists)) {
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
                                createFlag = true;
                                break;
                            }

                        }
                    }
                }
            }
        }
        if (!createFlag) {
            int amount = productItem.getAmount();
            double weight = productItem.getWeight();
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
                productItem.setWeight(productItem.getWeight() - logisticsRules.getWeightLimit());
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
            createLogisticsOrder(logisticsOrderBeans, productItem);
        }
        return null;
    }

    private boolean compitableMix(LogisticsRuleBean ruleBean, ProductItem item) {
        if (null == ruleBean) {
            return false;
        }
        if (null != ruleBean.getMixPriceLimit() && ruleBean.getMixPriceLimit() > item.getPrice()) {
            return false;
        }
        if (null != ruleBean.getMixWeightLimit() && ruleBean.getMixWeightLimit() > item.getWeight()) {
            return false;
        }
        if (null != ruleBean.getMixNumberLimit() && ruleBean.getMixNumberLimit() > item.getAmount()) {
            return false;
        }
        return true;
    }

    private boolean totalCompitableMix(List<ProductItem> list) {
        int amount = 0;
        double weight = 0;
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            amount += list.get(i).getAmount();
            weight += list.get(i).getWeight();
            price += list.get(i).getPrice();
        }
        for (int i = 0; i < list.size(); i++) {
            if (!compitableMix(list.get(i).getRuleBean(), new ProductItem(amount, weight, price))) {
                return false;
            }
        }
        return true;
    }
}
