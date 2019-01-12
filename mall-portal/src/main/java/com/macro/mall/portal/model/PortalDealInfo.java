package com.macro.mall.portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalDealInfo {
    // 商品总件数
    private int productNum;
    // 邮寄总重量
    private double postWeight;
    // 商品价格
    private double productPrice;
    // 运费
    private double postPrice;
    // 订单价格
    private double orderPrice;
    // 人民币价格
    private double orderCnyPrice;

}
