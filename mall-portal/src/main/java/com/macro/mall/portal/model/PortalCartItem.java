package com.macro.mall.portal.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PortalCartItem implements Serializable {
    private Long id;
    private Long productId;
    private String productName;
    private String pic;
    private BigDecimal cnyPrice;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal weight;
    // 是否上架
    private int publishStatus;
}