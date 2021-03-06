package com.macro.mall.portal.controller;

import com.macro.mall.model.UmsProductCollection;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.service.MemberCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "MemberCollectionController", description = "会员收藏管理")
@RequestMapping("/member/collection")
public class MemberCollectionController {
    @Autowired
    private MemberCollectionService memberCollectionService;

    @ApiOperation("添加商品收藏")
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody
    public Object addProduct(long productId) {
        UmsProductCollection collection = new UmsProductCollection();
        collection.setProductId(productId);
        int count = memberCollectionService.addProduct(collection);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("删除收藏商品")
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteProduct(Long id) {
        int count = memberCollectionService.deleteProduct(id);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("显示收藏列表")
    @RequestMapping(value = "/listProduct", method = RequestMethod.GET)
    @ResponseBody
    public Object listProduct() {
        return new CommonResult().success(memberCollectionService.listProduct());
    }
}
