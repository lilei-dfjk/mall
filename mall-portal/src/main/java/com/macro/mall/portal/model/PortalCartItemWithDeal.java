package com.macro.mall.portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalCartItemWithDeal {
    private List<PortalCartItem> carLists = new ArrayList<>();
    private PortalDealInfo dealInfo;
}
