package com.macro.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class OrderBean {
    private String userId;
    private String orderNo;
    private String logisticsType;
    private List<ProductItem> productItemList;
}
