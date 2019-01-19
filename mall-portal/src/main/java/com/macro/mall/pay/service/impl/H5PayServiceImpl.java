package com.macro.mall.pay.service.impl;

import com.macro.mall.pay.omipayFunction.JSAPIOrder;
import com.macro.mall.pay.service.H5PayService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class H5PayServiceImpl implements H5PayService {
    @Value("${pay.memberId}")
    private String memberId;
    @Value("${pay.memberkey}")
    private String memberKey;
    @Value("${pay.notifyUrl}")
    private String notifyUrl;
    @Value("${pay.redirectUrl}")
    private String redirectUrl;
    private static final String AUD = "AUD";
    private static final String ORDER_NAME = "澳绎商品";

    @Override
    public Map<String, String> getPayParam(double amount, String orderId) {
        JSONObject jsonObject = JSAPIOrder.MakeJSAPIOrder(memberId, memberKey, System.currentTimeMillis() + "", ORDER_NAME, AUD, (int)amount*1000, notifyUrl, redirectUrl, orderId, true);
        return jsonObject;
    }
}
