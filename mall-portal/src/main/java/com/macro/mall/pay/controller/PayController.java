package com.macro.mall.pay.controller;

import com.macro.mall.pay.service.H5PayService;
import com.macro.mall.portal.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@Api(tags = "PayController", description = "支付")
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private H5PayService payService;

    @ApiOperation("支付")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Object pay(String orderId, double amount) {
        Map<String, String> payParam = payService.getPayParam(amount, orderId);
        if (payParam.get("return_code").equals("SUCCESS")){
            String payUrl = payParam.get("pay_url");
            return new CommonResult().success(payUrl);
        }
        return new CommonResult().failed(payParam.get("error_msg"));

    }

    @RequestMapping(value = "success")
    public String success() {
        return "paySuccess";
    }
}
