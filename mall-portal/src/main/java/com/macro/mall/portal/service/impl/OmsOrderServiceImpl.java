package com.macro.mall.portal.service.impl;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import com.macro.mall.logistcs.cons.LogisticType;
import com.macro.mall.mapper.*;
import com.macro.mall.model.*;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.portal.dao.PortalOrderDao;
import com.macro.mall.portal.dao.PortalOrderItemDao;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ConfirmOrderBeanResult;
import com.macro.mall.portal.domain.OmsOrderDetail;
import com.macro.mall.portal.domain.OrderParam;
import com.macro.mall.portal.model.*;
import com.macro.mall.portal.service.*;
import com.macro.mall.portal.util.RedisLock;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Value("${redis.key.prefix.orderId}")
    private String REDIS_KEY_PREFIX_ORDER_ID;
    @Autowired
    private OmsCartItemMapper cartItemMapper;
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private OmsLogicOrderItemMapper logicOrderItemMapper;
    @Autowired
    private OmsLogisticOrderMapper logisticOrderMapper;
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PortalOrderItemDao orderItemDao;
    @Autowired
    private OmsOrderItemMapper orderItemMapper;
    @Autowired
    private OmsOrderMapper orderMapper;
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PortalOrderDao portalOrderDao;
    @Autowired
    private PortalProductService portalProductService;
    @Autowired
    private OmsOrderUnderPostMapper postMapper;
    @Autowired
    private PortalProductDao productDao;
    @Autowired
    private PmsProductLogisticRuleService productLogisticRuleService;
    @Autowired
    private OmsPromotionService promotionService;
    @Autowired
    private RateService rateService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private com.macro.mall.logistcs.service.ZhLogisticService zhLogisticService;

    @Override
    public void deleteOrderById(Long orderId) {
        OmsOrder omsOrder = orderMapper.selectByPrimaryKey(orderId);
        omsOrder.setStatus(OmsOrderStatus.DELETE.getValue());
        orderMapper.updateByPrimaryKey(omsOrder);
    }

    @Override
    public ConfirmOrderBeanResult generateConfirmOrder(List<Long> cardIds) {
        //获取用户收货地址列表
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list();
        PortalCartItemWithDeal portalCartInfo = getPortalCartInfo(cardIds);
        return new ConfirmOrderBeanResult(portalCartInfo.getCarLists(), memberReceiveAddressList, portalCartInfo.getDealInfo());
    }

    @Override
    public CommonResult getOrderId(Long orderId) {
        OmsOrder omsOrder = orderMapper.selectByPrimaryKey(orderId);
        if (null != omsOrder) {
            OmsOrderModel omsOrderModel = initOrderModel(omsOrder);
            return new CommonResult().success(omsOrderModel);
        }
        return null;
    }

    @Override
    public PortalCartItemWithDeal getPortalCartInfo(List<Long> cardIds) {
        UmsMember currentMember = memberService.getCurrentMember();
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(currentMember.getId());
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(cardIds)) {
            example.createCriteria().andIdIn(cardIds);
        }
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
                return new PortalCartItemWithDeal(productItems, portalDealInfo, logisticOrders);
            }
        }
        return null;
    }

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        PortalCartItemWithDeal portalCartInfo = this.getPortalCartInfo(orderParam.getIds());
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        //获取购物车及优惠信息
        UmsMember currentMember = memberService.getCurrentMember();
        if (null == portalCartInfo) {
            return new CommonResult().failed("购物车为空");
        }
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
        order.setTotalWeight(portalCartInfo.getDealInfo().getPostWeight());
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
        if (null == address) {
            return new CommonResult().failed("收货地址为空");
        }
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiveId(orderParam.getMemberReceiveAddressId());
        order.setReceiveIdentityFront(address.getIdentityFront());
        order.setReceiveIdentityBack(address.getIdentityBack());
        order.setReceiveIdentityNo(address.getIdentityNo());
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
        gernerateLogicOrder(portalCartInfo);
        return new CommonResult().success("下单成功", result);
    }

    /**
     * 判断下单商品是否都有库存
     */
    private boolean hasStock(List<ProductItem> carLists) {
        for (ProductItem productItem : carLists) {
            PmsProduct skuStock = pmsProductMapper.selectByPrimaryKey(productItem.getProductId());
            if (productItem.getNumber() >= (skuStock.getStock() - (skuStock.getLockStock() == null ? 0 : skuStock.getLockStock())) || productItem.getStock() <= 0) {
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
            lockStock(productItem);
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
//        sb.append(String.format("%02d", order.getSourceType()));
//        sb.append(String.format("%02d", order.getPayType()));
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

    private void gernerateLogicOrder(PortalCartItemWithDeal portalCartInfo) {
        List<LogisticsOrderBean> logisticsOrders = portalCartInfo.getLogisticsOrders();
        if (!CollectionUtils.isEmpty(logisticsOrders)) {
            logisticsOrders.stream().forEach(logisticsOrder -> {
                OmsLogisticOrder order = new OmsLogisticOrder();
                order.setExpressPrice(BigDecimal.valueOf(logisticsOrder.getExpressPrice()));
                order.setOrderNo(logisticsOrder.getOrderNo());
                order.setTotalNumber(logisticsOrder.getTotalNumber());
                order.setTotalPrice(BigDecimal.valueOf(logisticsOrder.getTotalPrice()));
                order.setTotalWeight(logisticsOrder.getTotalWeight());
                logisticOrderMapper.insert(order);
                List<ProductItem> productItemList = logisticsOrder.getProductItemList();
                productItemList.stream().forEach(productItem -> {
                    OmsLogicOrderItem item = new OmsLogicOrderItem();
                    item.setPic(productItem.getPic());
                    item.setLogicOrderId(order.getId());
                    item.setBrand(productItem.getBrand());
                    item.setNumber(productItem.getNumber());
                    item.setProductSn(productItem.getProductSn());
                    item.setProductType(productItem.getProductType());
                    item.setProductName(productItem.getProductName());
                    item.setPrice(BigDecimal.valueOf(productItem.getPrice()));
                    item.setWeight(BigDecimal.valueOf(productItem.getWeight()));
                    item.setCnyPrice(BigDecimal.valueOf(productItem.getCnyPrice()));
                    logicOrderItemMapper.insert(item);
                });
            });
        }

    }

    private void lockStock(ProductItem productItem) {
        RedisLock lock = new RedisLock(redisTemplate, "product.stock.lock." + productItem.getProductId(), 10000, 20000);
        try {
            if (lock.lock()) {
                PmsProduct skuStock = pmsProductMapper.selectByPrimaryKey(productItem.getProductId());
                skuStock.setLockStock((skuStock.getLockStock() == null ? 0 : skuStock.getLockStock()) + productItem.getNumber());
                pmsProductMapper.updateByPrimaryKeySelective(skuStock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
            //操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
            lock.unlock();
        }
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
        OmsOrderUnderPost omsOrderUnderPost = new OmsOrderUnderPost();
        omsOrderUnderPost.setCreateTime(new Date());
        omsOrderUnderPost.setOrderId(orderId);
        omsOrderUnderPost.setStatus((short) OmsPostStatus.INIT.getValue());
        postMapper.insert(omsOrderUnderPost);
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

    @Override
    public CommonResult listOrders(Integer status) {

        OmsOrderExample example = new OmsOrderExample();
        if (null != status) {
            example.createCriteria().andStatusEqualTo(status);
        }
        example.createCriteria().andMemberIdEqualTo(memberService.getCurrentMember().getId());
        List<OmsOrder> omsOrders = orderMapper.selectByExample(example);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(omsOrders)) {
            List<OmsOrderModel> modelList = omsOrders.stream().map(omsOrder -> initOrderModel(omsOrder)).collect(Collectors.toList());
            return new CommonResult().success(modelList);
        }
        return new CommonResult().success(null);
    }

    @Override
    public CommonResult orderNums() {
        List<Map<Integer, Integer>> integerIntegerMap = portalOrderDao.sumStatus(memberService.getCurrentMember().getId());
        if (!CollectionUtils.isEmpty(integerIntegerMap)) {
            Map<String, Integer> maps = new HashMap<>(integerIntegerMap.size());
            integerIntegerMap.stream().forEach(map -> {
                maps.put(OmsOrderStatus.getNameByValue(map.get("status")), map.get("num"));
                System.out.println(map);
            });
            return new CommonResult().success(maps);
        }
        return new CommonResult().success(null);
    }

    @Override
    public List<OmsLogisticOrderModel> getLogisticOrders(String orderSn) {
        OmsLogisticOrderExample example = new OmsLogisticOrderExample();
        example.createCriteria().andLogisticOrderNoEqualTo(orderSn);
        List<OmsLogisticOrder> omsLogisticOrders = logisticOrderMapper.selectByExample(example);
        List<OmsLogisticOrderModel> models = new ArrayList<>(omsLogisticOrders.size());
        omsLogisticOrders.stream().forEach(order -> {
            OmsLogisticOrderModel orderModel = new OmsLogisticOrderModel();
            BeanUtils.copyProperties(order, orderModel);
            OmsLogicOrderItemExample exampleItem = new OmsLogicOrderItemExample();
            exampleItem.createCriteria().andLogicOrderIdLessThanOrEqualTo(order.getId());
            List<OmsLogicOrderItem> omsLogicOrderItems = logicOrderItemMapper.selectByExample(exampleItem);
            orderModel.setItems(omsLogicOrderItems);
            models.add(orderModel);
        });
        return models;
    }

    private OmsOrderModel initOrderModel(OmsOrder omsOrder) {
        OmsOrderModel model = new OmsOrderModel();
        model.setOrderId(omsOrder.getId());
        model.setOrderNo(omsOrder.getOrderSn());
        model.setTotalPrice(omsOrder.getPayAmount());
        model.setPostPrice(omsOrder.getFreightAmount());
        model.setTotalCnyPrice(omsOrder.getPayAmount().multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())));
        model.setStatus(omsOrder.getStatus());
        model.setTotalWeight(null != omsOrder.getTotalWeight() ? omsOrder.getTotalWeight() : 0.00);
        model.setProductWeight(null == omsOrder.getProductWeight() ? 0.00 : omsOrder.getProductWeight());
        model.setPayTime(omsOrder.getPaymentTime());
        model.setRecieveTime(omsOrder.getReceiveTime());
        model.setCommentTime(omsOrder.getCommentTime());
        model.setCommentFlag(null != omsOrder.getCommentTime());
        model.setPayType(omsOrder.getPayType());
        OmsOrderItemExample example = new OmsOrderItemExample();
        example.createCriteria().andOrderSnEqualTo(omsOrder.getOrderSn());
        List<OmsOrderItem> omsOrderItems = orderItemMapper.selectByExample(example);
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(omsOrderItems)) {
            List<OmsOrderItemModel> orderItemModels = omsOrderItems.stream().map(omsOrderItem -> initOrderItemModel(omsOrderItem)).collect(Collectors.toList());
            model.setProducts(orderItemModels);
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(model.getProducts())) {
                int number = model.getProducts().stream().mapToInt(orderItemModel -> orderItemModel.getNumber()).sum();
                double productPrice = model.getProducts().stream().mapToDouble(orderItemModel -> orderItemModel.getPrice().doubleValue()).sum();
                model.setNumber(number);
                model.setProductPrice(productPrice);
            }
        }
        UmsMemberReceiveAddress address = initUserReceiveAddress(omsOrder);
        model.setAddress(address);
        return model;
    }

    private OmsOrderItemModel initOrderItemModel(OmsOrderItem omsOrderItem) {
        OmsOrderItemModel itemModel0 = new OmsOrderItemModel();
        itemModel0.setProductId(omsOrderItem.getProductId());
        itemModel0.setProductPic(omsOrderItem.getProductPic());
        itemModel0.setProductName(omsOrderItem.getProductName());
        itemModel0.setNumber(omsOrderItem.getProductQuantity());
        itemModel0.setPrice(omsOrderItem.getProductPrice());
        return itemModel0;
    }

    private UmsMemberReceiveAddress initUserReceiveAddress(OmsOrder omsOrder) {
        UmsMemberReceiveAddress address = new UmsMemberReceiveAddress();
        address.setName(omsOrder.getReceiverName());
        address.setRegion(omsOrder.getReceiverRegion());
        address.setPostCode(omsOrder.getReceiverPostCode());
        address.setPhoneNumber(omsOrder.getReceiverPhone());
        address.setDetailAddress(omsOrder.getReceiverDetailAddress());
        address.setProvince(omsOrder.getReceiverProvince());
        address.setCity(omsOrder.getReceiverCity());
        return address;
    }

    private ProductItem initProductItem(OmsCartItem item, LogisticType logisticType) {
        PmsProduct productInfo = portalProductService.getProductInfo(item.getProductId());
        LogisticsRuleBean logisticRuleBean = productLogisticRuleService.getLogisticRuleBean(item.getProductId(), logisticType);
        ProductItem productItem = new ProductItem();
        productItem.setCartId(item.getId());
        productItem.setProductId(productInfo.getId());
        productItem.setNumber(item.getQuantity());
        productItem.setProductSn(productInfo.getProductSn());
        productItem.setRuleBean(logisticRuleBean);
        productItem.setPic(productInfo.getPic());
        productItem.setStock(productInfo.getStock());
//        productItem.setProductSkuId(item.getProductSkuId());
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
}
