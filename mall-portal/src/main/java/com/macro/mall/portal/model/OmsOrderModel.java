package com.macro.mall.portal.model;

import com.macro.mall.model.OmsOrderItem;
import com.macro.mall.model.UmsMemberReceiveAddress;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OmsOrderModel implements Serializable {
    private Long orderId;
    private String orderNo;
    private int number;
    private double productPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalCnyPrice;
    private double totalWeight;
    private double productWeight;
    private BigDecimal postPrice;
    private List<OmsOrderItemModel> products;
    private UmsMemberReceiveAddress address;
    private int status;
    private Date payTime;
    private Date deliveryTime;
    private Date recieveTime;
    private Date commentTime;
    private Integer payType;
    private boolean commentFlag;
}
