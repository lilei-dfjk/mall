package com.macro.mall.portal.controller;

import com.macro.mall.model.OmsCartItem;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.model.PortalCartItemWithDeal;
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Api(tags = "OmsCartItemController", description = "购物车管理")
@RequestMapping("/cart")
public class OmsCartItemController {
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("添加商品到购物车")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object add(long productId, int quantity, double price) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(BigDecimal.valueOf(price));
        int count = cartItemService.add(cartItem);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("清空购物车")
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    @ResponseBody
    public Object clear() {
        int count = cartItemService.clear(memberService.getCurrentMember().getId());
        if (count > 0) {
            return new CommonResult().success(cartItemService.lists(memberService.getCurrentMember().getId()));
        }
        return new CommonResult().failed();
    }

    @ApiOperation("计算商品费用")
    @RequestMapping(value = "/deal", method = RequestMethod.GET)
    @ResponseBody
    public Object deal(@RequestParam("ids") String cartIds) {
        if (!StringUtils.isEmpty(cartIds)) {
            List<Long> strings = Arrays.asList(cartIds.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
            PortalCartItemWithDeal deal = cartItemService.lists(memberService.getCurrentMember().getId(), strings);
            return new CommonResult().success(deal.getDealInfo());
        }
        return new CommonResult().success(null);
    }

    @ApiOperation("删除购物车中的某个商品")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@RequestParam("ids") List<Long> ids) {
        int count = cartItemService.delete(memberService.getCurrentMember().getId(), ids);
        if (count > 0) {
            return new CommonResult().success(cartItemService.lists(memberService.getCurrentMember().getId()));
        }
        return new CommonResult().failed();
    }

    @ApiOperation("获取购物车中某个商品的规格,用于重选规格")
    @RequestMapping(value = "/getProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getCartProduct(@PathVariable Long productId) {
        CartProduct cartProduct = cartItemService.getCartProduct(productId);
        return new CommonResult().success(cartProduct);
    }

    @ApiOperation("获取某个会员的购物车列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        PortalCartItemWithDeal deal = cartItemService.lists(memberService.getCurrentMember().getId());
        return new CommonResult().success(deal);
    }

    @ApiOperation("获取某个会员的购物车列表,包括促销信息")
    @RequestMapping(value = "/list/promotion", method = RequestMethod.GET)
    @ResponseBody
    public Object listPromotion() {
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(memberService.getCurrentMember().getId());
        return new CommonResult().success(cartPromotionItemList);
    }

    @RequestMapping(value = "/nums", method = RequestMethod.GET)
    @ResponseBody
    public Object nums() {
        return new CommonResult().success(cartItemService.nums(memberService.getCurrentMember().getId()));
    }

    @ApiOperation("修改购物车中商品的规格")
    @RequestMapping(value = "/update/attr", method = RequestMethod.POST)
    @ResponseBody
    public Object updateAttr(@RequestBody OmsCartItem cartItem) {
        int count = cartItemService.updateAttr(cartItem);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改购物车中某个商品的数量")
    @RequestMapping(value = "/update/quantity")
    @ResponseBody
    public Object updateQuantity(@RequestParam Long cartId,
                                 @RequestParam Integer quantity) {
        int count = cartItemService.updateQuantity(cartId, memberService.getCurrentMember().getId(), quantity);
        if (count > 0) {
            return new CommonResult().success(cartItemService.lists(memberService.getCurrentMember().getId()));
        }
        return new CommonResult().failed();
    }
}
