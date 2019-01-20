package com.macro.mall.logistcs.bean;

import com.macro.mall.logistcs.cons.LogisticType;
import lombok.Data;

import java.util.List;

@Data
public class OrderBean {
    private Long userId;
    private String orderNo;
    private LogisticType logisticsType;
    private List<ProductItem> productItemList;
}
