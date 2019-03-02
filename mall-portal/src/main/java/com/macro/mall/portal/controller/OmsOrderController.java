package com.macro.mall.portal.controller;

import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderBeanResult;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Api(tags = "OmsOrderController", description = "订单管理")
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService portalOrderService;

    @ApiOperation("取消单个超时订单")
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelOrder(Long orderId) {
        portalOrderService.sendDelayMessageCancelOrder(orderId);
        return new CommonResult().success(null);
    }

    @ApiOperation("自动取消超时订单")
    @RequestMapping(value = "/cancelTimeOutOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelTimeOutOrder() {
        return portalOrderService.cancelTimeOutOrder();
    }

    @ApiOperation("删除订单")
    @RequestMapping(value = "/delete/{orderId}", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable Long orderId) {
        portalOrderService.deleteOrderById(orderId);
        return new CommonResult().success("删除成功");
    }

    @ApiOperation("根据购物车信息生成确认单信息")
    @RequestMapping(value = "/generateConfirmOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object generateConfirmOrder(@RequestParam("ids") String ids) {
        if (!StringUtils.isEmpty(ids)) {
            List<Long> strings = Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
            ConfirmOrderBeanResult confirmOrderResult = portalOrderService.generateConfirmOrder(strings);
            return new CommonResult().success(confirmOrderResult);
        }
        return new CommonResult().failed("ids 不能为空");
    }

    @ApiOperation("根据购物车信息生成订单")
    @RequestMapping(value = "/generateOrder", method = RequestMethod.POST)
    @ResponseBody
    public Object generateOrder(Long memberReceiveAddressId, String ids) {
        List<Long> strings = Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList());
        OrderParam orderParam = new OrderParam();
        orderParam.setIds(strings);
        orderParam.setMemberReceiveAddressId(memberReceiveAddressId);
        return portalOrderService.generateOrder(orderParam);
    }

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     *
     * @param status
     * @return
     */
    @ApiOperation("获取订单列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false) Integer status) {
        return portalOrderService.listOrders(status);
    }

    @ApiOperation("获取详情")
    @RequestMapping(value = "/info/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@PathVariable Long orderId) {
        return portalOrderService.getOrderId(orderId);
    }

    @ApiOperation("获取订单数量")
    @RequestMapping(value = "/nums", method = RequestMethod.GET)
    @ResponseBody
    public Object orderNums() {
        return portalOrderService.orderNums();
    }

    @ApiOperation("支付成功的回调")
    @RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
    @ResponseBody
    public Object paySuccess(@RequestParam Long orderId) {
        return portalOrderService.paySuccess(orderId);
    }
}
