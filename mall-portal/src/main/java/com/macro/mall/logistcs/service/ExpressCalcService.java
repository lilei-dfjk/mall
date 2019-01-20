package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.cons.LogisticType;

import java.math.BigDecimal;
import java.util.List;

public interface ExpressCalcService {

    void getExpressPrice(LogisticType logisticType, List<LogisticsOrderBean> orders);

    double getExpressPrice(LogisticType logisticType, LogisticsOrderBean orderBean);
}
