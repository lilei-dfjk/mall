package com.macro.mall.portal.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OmsOrderItemModel implements Serializable {
    private Long productId;
    private String productPic;
    private String productName;
}
