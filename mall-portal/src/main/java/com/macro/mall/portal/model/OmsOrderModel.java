package com.macro.mall.portal.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OmsOrderModel implements Serializable {
    private String orderNo;
    private int number;
    private BigDecimal price;
    private List products;
    private int status;
}
