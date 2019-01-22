package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderBeanResult;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.model.PortalCartItemWithDeal;
import org.springframework.transaction.annotation.Transactional;

public interface OmsOrderService {
    /**
     * 根据用户购物车信息生成确认单信息
     */
    ConfirmOrderBeanResult generateConfirmOrder();

    PortalCartItemWithDeal getPortalCartInfo();

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 支付成功后的回调
     */
    @Transactional
    CommonResult paySuccess(Long orderId);

    /**
     * 自动取消超时订单
     */
    @Transactional
    CommonResult cancelTimeOutOrder();

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);

    /**
     * 发送延迟消息取消订单
     */
    void sendDelayMessageCancelOrder(Long orderId);
}
