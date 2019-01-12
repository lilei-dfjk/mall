package com.macro.mall.search.controller;

import com.macro.mall.search.domain.CommonResult;
import com.macro.mall.search.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 搜索商品管理Controller
 * Created by macro on 2018/6/19.
 */
@Controller
@Api(tags = "EsProductController", description = "搜索商品管理")
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @ApiOperation(value = "获取所有省市信息")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Object citys() {
        return new CommonResult().success(cityService.listAllProvice());
    }
}
