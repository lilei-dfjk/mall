package com.macro.mall.controller;

import com.macro.mall.dto.CommonResult;
import com.macro.mall.service.PmsLogisticRuleService;
import com.macro.mall.service.PmsProductLogisticRuleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "PmsProductLogisRuleController", description = "商品物流规则配置")
@RequestMapping("/product/logistic/rule")
public class PmsProductLogisRuleController {
    @Autowired
    private PmsProductLogisticRuleService pmsProductLogisticRuleService;
    @Autowired
    private PmsLogisticRuleService ruleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(Short logisticType) {
        return new CommonResult().pageSuccess(ruleService.listRulesByLogisticType(logisticType));
    }

    @RequestMapping(value = "/rules/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public Object listRulesByProductId(@PathVariable Long productId) {
        return new CommonResult().pageSuccess(pmsProductLogisticRuleService.listAllRules(productId));
    }

}
