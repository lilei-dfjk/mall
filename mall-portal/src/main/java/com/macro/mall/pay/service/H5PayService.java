package com.macro.mall.pay.service;

import java.util.Map;

public interface H5PayService {
    Map<String, String> getPayParam(double amount, String orderId);
}
