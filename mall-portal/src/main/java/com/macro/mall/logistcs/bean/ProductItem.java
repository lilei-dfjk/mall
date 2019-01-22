package com.macro.mall.logistcs.bean;

import com.macro.mall.logistcs.cons.LogisticType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {
    private String productId;
    private String productName;
    private String productType;
    private String brand;
    private int number;
    private double weight;
    private double price;
    private LogisticType logisticType;
    private String ruleType;
    private String ruleBrandType;
    private LogisticsRuleBean ruleBean;
    private String pic;
    private double cnyPrice;
    // 是否上架
    private int publishStatus;

    public ProductItem(int number, double weight, double price) {
        this.number = number;
        this.weight = weight;
        this.price = price;
    }
    public ProductItem(int number, double weight, double price, String ruleType, String ruleBrandType, LogisticType logisticType) {
        this.number = number;
        this.weight = weight;
        this.logisticType = logisticType;
        this.price = price;
        this.ruleType = ruleType;
        this.ruleBrandType = ruleBrandType;
    }
}
