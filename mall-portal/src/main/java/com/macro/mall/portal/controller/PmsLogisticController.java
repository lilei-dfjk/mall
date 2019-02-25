package com.macro.mall.portal.controller;

import com.macro.mall.logistcs.service.LogisticApiService;
import com.macro.mall.portal.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "PmsLogisticController", description = "物流轨迹")
@RequestMapping("/logistic")
public class PmsLogisticController {

    @Autowired
    private LogisticApiService logisticApiService;

    @ApiOperation("物流轨迹")
    @RequestMapping(value = "/track", method = RequestMethod.GET)
    @ResponseBody
    public Object track(String logisticNo) {
        return new CommonResult().success(logisticApiService.getLogisticTrack(logisticNo));
    }
}
