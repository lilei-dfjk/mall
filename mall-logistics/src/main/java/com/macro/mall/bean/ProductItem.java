package com.macro.mall.bean;

import lombok.Data;

@Data
public class ProductItem {
    private String productId;
    private String productName;
    private String productType;
    private String brand;
    private int amount;
    private int weight;
    private double price;
}
