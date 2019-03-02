package com.macro.mall.portal.model;

import com.macro.mall.model.OmsOrderItem;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OmsOrderModel implements Serializable {
    private Long orderId;
    private String orderNo;
    private int number;
    private double productPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalCnyPrice;
    private BigDecimal totalWeight;
    private BigDecimal productWeight;
    private BigDecimal postPrice;
    private List<OmsOrderItemModel> products;
    private int status;
}
