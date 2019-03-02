package com.macro.mall.portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OmsOrderItemModel implements Serializable {
    private Long productId;
    private String productPic;
    private String productName;
    private Integer number;
    private BigDecimal price;
    private double weight;
}
