package com.macro.mall.logistcs.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {
    private String productId;
    private String productName;
    private String productType;
    private String brand;
    private int amount;
    private double weight;
    private double price;
    private String ruleType;
    private String ruleBrandType;
    private LogisticsRuleBean ruleBean;

    public ProductItem(int amount, double weight, double price) {
        this.amount = amount;
        this.weight = weight;
        this.price = price;
    }
}
