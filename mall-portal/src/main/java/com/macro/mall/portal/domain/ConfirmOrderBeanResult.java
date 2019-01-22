package com.macro.mall.portal.domain;

import com.macro.mall.logistcs.bean.ProductItem;
import com.macro.mall.model.UmsMemberReceiveAddress;
import com.macro.mall.portal.model.PortalDealInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrderBeanResult {
    //包含优惠信息的购物车信息
    private List<ProductItem> cartList;
    //用户收货地址列表
    private List<UmsMemberReceiveAddress> memberReceiveAddressList;
    //计算的金额
    private PortalDealInfo portalDealInfo;

}
