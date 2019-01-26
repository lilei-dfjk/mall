package com.macro.mall.portal.service.impl;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import com.macro.mall.logistcs.cons.LogisticType;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.mapper.OmsOrderMapper;
import com.macro.mall.mapper.PmsSkuStockMapper;
import com.macro.mall.model.*;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.portal.dao.PortalOrderDao;
import com.macro.mall.portal.dao.PortalOrderItemDao;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderBeanResult;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.model.PortalCartItemWithDeal;
import com.macro.mall.portal.model.PortalDealInfo;
import com.macro.mall.portal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private OmsCartItemMapper cartItemMapper;
    @Autowired
    private PortalProductDao productDao;
    @Autowired
    private PortalOrderItemDao orderItemDao;
    @Autowired
    private OmsPromotionService promotionService;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private PortalProductService portalProductService;
    @Autowired
    private RateService rateService;
    @Autowired
    private PortalOrderDao portalOrderDao;
    @Autowired
    private OmsOrderMapper orderMapper;
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Value("${redis.key.prefix.orderId}")
    private String REDIS_KEY_PREFIX_ORDER_ID;
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private PmsProductLogisticRuleService productLogisticRuleService;
    @Autowired
    private com.macro.mall.logistcs.service.ZhLogisticService zhLogisticService;


    @Override
    public ConfirmOrderBeanResult generateConfirmOrder(List<Long> cardIds) {
        //获取用户收货地址列表
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list();
        PortalCartItemWithDeal portalCartInfo = getPortalCartInfo(cardIds);
        return new ConfirmOrderBeanResult(portalCartInfo.getCarLists(), memberReceiveAddressList, portalCartInfo.getDealInfo());
    }

    @Override
    public PortalCartItemWithDeal getPortalCartInfo(List<Long> cardIds) {
        UmsMember currentMember = memberService.getCurrentMember();
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(currentMember.getId());
        example.createCriteria().andIdIn(cardIds);
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
        productItem.setProductSn(productInfo.getProductSn());
        productItem.setRuleBean(logisticRuleBean);
        productItem.setPic(productInfo.getPic());
        productItem.setStock(productInfo.getStock());
        productItem.setProductSkuId(item.getProductSkuId());
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
        PortalCartItemWithDeal portalCartInfo = this.getPortalCartInfo(orderParam.getIds());
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        //获取购物车及优惠信息
        UmsMember currentMember = memberService.getCurrentMember();
        List<ProductItem> carLists = portalCartInfo.getCarLists();
        for (ProductItem cartPromotionItem : carLists) {
            //生成下单商品信息
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setProductId(cartPromotionItem.getProductId());
            orderItem.setProductName(cartPromotionItem.getProductName());
            orderItem.setProductPic(cartPromotionItem.getPic());
            orderItem.setProductBrand(cartPromotionItem.getBrand());
            orderItem.setProductSn(cartPromotionItem.getProductSn());
            orderItem.setProductPrice(BigDecimal.valueOf(cartPromotionItem.getPrice()));
            orderItem.setProductQuantity(cartPromotionItem.getNumber());
            orderItemList.add(orderItem);
        }
        //判断购物车中商品是否都有库存
        if (!hasStock(carLists)) {
            return new CommonResult().failed("库存不足，无法下单");
        }
        //进行库存锁定
        lockStock(carLists);
        //根据商品合计、运费、活动优惠、优惠券、积分计算应付金额
        OmsOrder order = new OmsOrder();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(BigDecimal.valueOf(portalCartInfo.getDealInfo().getOrderPrice()));
        order.setFreightAmount(BigDecimal.valueOf(portalCartInfo.getDealInfo().getPostPrice()));
        order.setPayAmount(order.getTotalAmount().add(order.getFreightAmount()));
        //转化为订单信息并插入数据库
        order.setMemberId(currentMember.getId());
        order.setCreateTime(new Date());
        order.setMemberUsername(currentMember.getUsername());
        //支付方式：0->未支付；1->支付宝；2->微信
        order.setPayType(orderParam.getPayType());
        //订单来源：0->PC订单；1->app订单
        order.setSourceType(1);
        //订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        //订单类型：0->正常订单；1->秒杀订单
        order.setOrderType(0);
        //收货人信息：姓名、电话、邮编、地址
        UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(orderParam.getMemberReceiveAddressId());
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        //0->未确认；1->已确认
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        //计算赠送积分
        //生成订单号
        order.setOrderSn(generateOrderSn(order));
        //插入order表和order_item表
        orderMapper.insert(order);
        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemDao.insertList(orderItemList);
        //删除购物车中的下单商品
        deleteCartItemList(orderParam.getIds(), currentMember);
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("orderItemList", orderItemList);
        return new CommonResult().success("下单成功", result);
    }

    /**
     * 判断下单商品是否都有库存
     */
    private boolean hasStock(List<ProductItem> carLists) {
        for (ProductItem productItem : carLists) {
            PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(productItem.getProductSkuId());
            if (productItem.getNumber() >= (skuStock.getStock() - skuStock.getLockStock()) || productItem.getStock() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 锁定下单商品的所有库存
     */
    private void lockStock(List<ProductItem> carLists) {
        for (ProductItem productItem : carLists) {
            PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(productItem.getProductSkuId());
            skuStock.setLockStock(skuStock.getLockStock() + productItem.getNumber());
            skuStockMapper.updateByPrimaryKeySelective(skuStock);
        }
    }

    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     */
    private String generateOrderSn(OmsOrder order) {
        StringBuilder sb = new StringBuilder();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = REDIS_KEY_PREFIX_ORDER_ID + date;
        Long increment = redisService.increment(key, 1);
        sb.append(date);
        sb.append(String.format("%02d", order.getSourceType()));
        sb.append(String.format("%02d", order.getPayType()));
        String incrementStr = increment.toString();
        if (incrementStr.length() <= 6) {
            sb.append(String.format("%06d", increment));
        } else {
            sb.append(incrementStr);
        }
        return sb.toString();
    }

    /**
     * 删除下单商品的购物车信息
     */
    private void deleteCartItemList(List<Long> ids, UmsMember currentMember) {
        cartItemService.delete(currentMember.getId(), ids);
    }


    @Override
    public CommonResult paySuccess(Long orderId) {
        //修改订单支付状态
        OmsOrder order = new OmsOrder();
        order.setId(orderId);
        order.setStatus(1);
        order.setPaymentTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
        //恢复所有下单商品的锁定库存，扣减真实库存
        OmsOrderDetail orderDetail = portalOrderDao.getDetail(orderId);
        int count = portalOrderDao.updateSkuStock(orderDetail.getOrderItemList());
        return new CommonResult().success("支付成功", count);
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
