package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.cons.LogisticType;

public interface LogisticApiService {
    String recordLogistic(OrderBean orderBean);
    void uploadIdentify();
    void getLogisticTrack(String expressNo);
}
