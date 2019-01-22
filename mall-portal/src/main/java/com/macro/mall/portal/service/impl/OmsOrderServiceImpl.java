package com.macro.mall.portal.service.impl;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import com.macro.mall.logistcs.cons.LogisticType;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.model.*;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderBeanResult;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.model.PortalCartItemWithDeal;
import com.macro.mall.portal.model.PortalDealInfo;
import com.macro.mall.portal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private OmsCartItemMapper cartItemMapper;
    @Autowired
    private PortalProductDao productDao;
    @Autowired
    private OmsPromotionService promotionService;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PortalProductService portalProductService;
    @Autowired
    private RateService rateService;
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private PmsProductLogisticRuleService productLogisticRuleService;
    @Autowired
    private com.macro.mall.logistcs.service.ZhLogisticService zhLogisticService;


    @Override
    public ConfirmOrderBeanResult generateConfirmOrder() {
        //获取用户收货地址列表
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list();
        PortalCartItemWithDeal portalCartInfo = getPortalCartInfo();
        return new ConfirmOrderBeanResult(portalCartInfo.getCarLists(), memberReceiveAddressList, portalCartInfo.getDealInfo());
    }

    @Override
    public PortalCartItemWithDeal getPortalCartInfo() {
        UmsMember currentMember = memberService.getCurrentMember();
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(currentMember.getId());
        List<OmsCartItem> omsCartItems = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(omsCartItems)) {
            OrderBean orderBean = new OrderBean();
            orderBean.setUserId(currentMember.getId());
            // 默认中环
            orderBean.setLogisticsType(LogisticType.ZH);
            List<ProductItem> productItems = omsCartItems.stream().map(portalCartItem -> initProductItem(portalCartItem, orderBean.getLogisticsType())).collect(Collectors.toList());
            orderBean.setProductItemList(productItems);
            List<LogisticsOrderBean> logisticOrders = zhLogisticService.getLogisticOrders(orderBean);
            if (!CollectionUtils.isEmpty(productItems)) {
                int productNum;
                // 邮寄总重量
                double postWeight;
                // 商品价格
                double productPrice;
                // 运费
                double postPrice;
                // 订单价格
                double orderPrice;
                // 人民币价格
                double orderCnyPrice;
                productNum = logisticOrders.stream().mapToInt(logisticsOrderBean -> logisticsOrderBean.getTotalNumber()).sum();
                postWeight = logisticOrders.stream().mapToDouble(logisticsOrderBean -> logisticsOrderBean.getTotalWeight()).sum();
                productPrice = logisticOrders.stream().mapToDouble(logisticsOrderBean -> logisticsOrderBean.getTotalPrice()).sum();
                postPrice = logisticOrders.stream().mapToDouble(logisticsOrderBean -> logisticsOrderBean.getExpressPrice()).sum();
                orderPrice = productPrice + postPrice;
                orderCnyPrice = BigDecimal.valueOf(orderPrice).multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())).doubleValue();
                PortalDealInfo portalDealInfo = new PortalDealInfo(productNum, postWeight, productPrice, postPrice, orderPrice, orderCnyPrice);
                return new PortalCartItemWithDeal(productItems, portalDealInfo);
            }
        }
        return null;
    }

    private ProductItem initProductItem(OmsCartItem item, LogisticType logisticType) {
        PmsProduct productInfo = portalProductService.getProductInfo(item.getProductId());
        LogisticsRuleBean logisticRuleBean = productLogisticRuleService.getLogisticRuleBean(item.getProductId(), logisticType);
        ProductItem productItem = new ProductItem();
        productItem.setNumber(item.getQuantity());
        productItem.setRuleBean(logisticRuleBean);
        productItem.setPic(productInfo.getPic());
        productItem.setProductName(productInfo.getName());
        productItem.setPrice(item.getPrice().doubleValue());
        productItem.setWeight(productInfo.getWeight().doubleValue());
        productItem.setPublishStatus(productInfo.getPublishStatus());
        productItem.setCnyPrice(productInfo.getPrice().multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())).doubleValue());
        if (null != logisticRuleBean) {
            productItem.setRuleType(logisticRuleBean.getLogisType());
            productItem.setRuleBrandType(logisticRuleBean.getBrandRuleType());
        }
        return productItem;
    }

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        return null;
    }

    @Override
    public CommonResult paySuccess(Long orderId) {
        return null;
    }

    @Override
    public CommonResult cancelTimeOutOrder() {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {

    }

    @Override
    public void sendDelayMessageCancelOrder(Long orderId) {

    }
}
