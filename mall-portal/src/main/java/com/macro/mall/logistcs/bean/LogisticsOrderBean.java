package com.macro.mall.logistcs.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LogisticsOrderBean {
    private String orderNo;
    private String userId;
    private String logisticsOrderNo;
    private List<ProductItem> productItemList = new ArrayList<>();
    private boolean full = false;
    private double totalWeight;
    private int totalNumber;
    private double totalPrice;
    // 运费
    private double expressPrice;
    /**
     * 是否包含杂货
     */
    private boolean otherFlag;
    /**
     * 包裹内必须满足每一项混装规则
     */
    List<LogisticsRuleBean> rules;
}
