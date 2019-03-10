package com.macro.mall.portal.model;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalCartItemWithDeal {
    private List<ProductItem> carLists = new ArrayList<>();
    private PortalDealInfo dealInfo;
    private List<LogisticsOrderBean> logisticsOrders;
}
