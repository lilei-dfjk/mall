package com.macro.mall.portal.model;

import com.macro.mall.model.OmsLogicOrderItem;
import com.macro.mall.model.OmsLogisticOrder;

import java.util.List;

public class OmsLogisticOrderModel extends OmsLogisticOrder {
    private List<OmsLogicOrderItem> items;

    public List<OmsLogicOrderItem> getItems() {
        return items;
    }

    public void setItems(List<OmsLogicOrderItem> items) {
        this.items = items;
    }
}
