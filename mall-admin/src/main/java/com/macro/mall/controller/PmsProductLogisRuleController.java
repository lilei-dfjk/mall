package com.macro.mall.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.dto.OmsOrderQueryParam;
import com.macro.mall.model.OmsOrder;
import com.macro.mall.service.PmsProductLogisticRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(tags = "PmsProductLogisRuleController", description = "商品物流规则配置")
@RequestMapping("/product/logistic/rule")
public class PmsProductLogisRuleController {
    @Autowired
    private PmsProductLogisticRuleService ruleService;

    @ApiOperation("查询订单")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(String logisticType) {
        ruleService.listRulesByLogisticType(logisticType);

//        List<OmsOrder> orderList = orderService.list(queryParam, pageSize, pageNum);
//        return new CommonResult().pageSuccess(orderList);
        return null;
    }

}
