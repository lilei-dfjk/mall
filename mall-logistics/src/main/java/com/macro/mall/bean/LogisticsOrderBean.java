package com.macro.mall.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LogisticsOrderBean {
    private String orderNo;
    private String userId;
    private String logisticsOrderNo;
    private List<ProductItem> productItemList = new ArrayList<>();
    private boolean full;
    private int totalWeight;
    private int totalNumber;
    private double totalPrice;
}
