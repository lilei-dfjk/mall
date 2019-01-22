package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.OrderBean;

import java.util.List;

public interface ZhLogisticService {

    public List<LogisticsOrderBean> getLogisticOrders(OrderBean orderBean);

}
