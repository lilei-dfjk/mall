package com.macro.mall.logistcs.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macro.mall.logistcs.cons.LogisticType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {
    private long cartId;
    private long productId;
    private String productName;
    private String productType;
    private String productSn;
//    private long productSkuId;
    private int stock;
    private String brand;
    private int number;
    private double weight;
    private double price;
    @JsonIgnore
    private LogisticType logisticType;
    @JsonIgnore
    private String ruleType;
    @JsonIgnore
    private String ruleBrandType;
    @JsonIgnore
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
