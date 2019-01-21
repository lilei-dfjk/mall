package com.macro.mall.portal.service.impl;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import com.macro.mall.logistcs.cons.LogisticType;
import com.macro.mall.mapper.OmsCartItemMapper;
import com.macro.mall.model.OmsCartItem;
import com.macro.mall.model.OmsCartItemExample;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.pay.rate.RateService;
import com.macro.mall.portal.dao.PortalProductDao;
import com.macro.mall.portal.domain.CartProduct;
import com.macro.mall.portal.domain.CartPromotionItem;
import com.macro.mall.portal.model.PortalCartItem;
import com.macro.mall.portal.model.PortalCartItemWithDeal;
import com.macro.mall.portal.model.PortalDealInfo;
import com.macro.mall.portal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {
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
    private PmsProductLogisticRuleService productLogisticRuleService;
    @Autowired
    private com.macro.mall.logistcs.service.ZhLogisticService ZhLogisticService;


    @Override
    public int add(OmsCartItem cartItem) {
        int count;
        PmsProduct productInfo = portalProductService.getProductInfo(cartItem.getProductId());
        if (null == productInfo) {
            return 0;
        }
        UmsMember currentMember = memberService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
//        cartItem.setProductPic(productInfo.getPic());
//        cartItem.setCnyPrice(productInfo.getPrice().divide(BigDecimal.valueOf(rateService.getAuToCnyRate()), 2));
        cartItem.setProductSn(productInfo.getProductSn());
        cartItem.setProductName(productInfo.getName());
        cartItem.setStock(productInfo.getStock());

        cartItem.setMemberNickname(currentMember.getNickname());
        cartItem.setDeleteStatus(0);
        OmsCartItem existCartItem = getCartItem(cartItem);
        if (existCartItem == null) {
            cartItem.setCreateDate(new Date());
            count = cartItemMapper.insert(cartItem);
        } else {
            cartItem.setModifyDate(new Date());
            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateByPrimaryKey(existCartItem);
        }
        return count;
    }

    /**
     * 根据会员id,商品id和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
                .andProductIdEqualTo(cartItem.getProductId()).andDeleteStatusEqualTo(0);
        if (!StringUtils.isEmpty(cartItem.getSp1())) {
            criteria.andSp1EqualTo(cartItem.getSp1());
        }
        if (!StringUtils.isEmpty(cartItem.getSp2())) {
            criteria.andSp2EqualTo(cartItem.getSp2());
        }
        if (!StringUtils.isEmpty(cartItem.getSp3())) {
            criteria.andSp3EqualTo(cartItem.getSp3());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    @Override
    public List<OmsCartItem> list(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId);
        return cartItemMapper.selectByExample(example);
    }

    @Override
    public PortalCartItemWithDeal lists(Long memberId) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andMemberIdEqualTo(memberId);
        List<OmsCartItem> omsCartItems = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(omsCartItems)) {
//            List<PortalCartItem> portalCartItems = omsCartItems.stream().map(omsCartItem -> initPortalCartItem(omsCartItem)).collect(Collectors.toList());
            OrderBean orderBean = new OrderBean();
            orderBean.setUserId(memberId);
            // 默认中环
            orderBean.setLogisticsType(LogisticType.ZH);
            List<ProductItem> productItems = omsCartItems.stream().map(portalCartItem -> initProductItem(portalCartItem, orderBean.getLogisticsType())).collect(Collectors.toList());
            orderBean.setProductItemList(productItems);
            List<LogisticsOrderBean> logisticOrders = ZhLogisticService.getLogisticOrders(orderBean);
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
        return new PortalCartItemWithDeal();
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

    private PortalCartItem initPortalCartItem(OmsCartItem item) {
        PmsProduct productInfo = portalProductService.getProductInfo(item.getProductId());
        PortalCartItem portalCartItem = new PortalCartItem();
        portalCartItem.setId(item.getId());
        portalCartItem.setPic(productInfo.getPic());
        portalCartItem.setQuantity(item.getQuantity());
        portalCartItem.setPrice(item.getPrice());
        portalCartItem.setProductId(item.getProductId());
        portalCartItem.setWeight(productInfo.getWeight());
        portalCartItem.setProductName(productInfo.getName());
        portalCartItem.setPublishStatus(productInfo.getPublishStatus());
        portalCartItem.setCnyPrice(productInfo.getPrice().multiply(BigDecimal.valueOf(rateService.getAuToCnyRate())));
        return portalCartItem;
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId) {
        List<OmsCartItem> cartItemList = list(memberId);
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cartItemList)) {
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andIdEqualTo(id).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(cartItem, example);
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andIdIn(ids).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return productDao.getCartProduct(productId);
    }

    @Override
    public int updateAttr(OmsCartItem cartItem) {
        //删除原购物车信息
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        cartItem.setId(null);
        add(cartItem);
        return 1;
    }

    @Override
    public int clear(Long memberId) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }
}
