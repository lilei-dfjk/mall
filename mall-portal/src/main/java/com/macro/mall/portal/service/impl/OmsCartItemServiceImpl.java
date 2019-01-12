package com.macro.mall.portal.service.impl;

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
import com.macro.mall.portal.service.OmsCartItemService;
import com.macro.mall.portal.service.OmsPromotionService;
import com.macro.mall.portal.service.PortalProductService;
import com.macro.mall.portal.service.UmsMemberService;
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

    @Override
    public int add(OmsCartItem cartItem) {
        int count;
        PmsProduct productInfo = portalProductService.getProductInfo(cartItem.getProductId());
        if (null == productInfo) {
            return 0;
        }
        UmsMember currentMember = memberService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setPrice(productInfo.getPrice());
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
            List<PortalCartItem> portalCartItems = omsCartItems.stream().map(omsCartItem -> initPortalCartItem(omsCartItem)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(portalCartItems)) {
                int productNum;
                // 邮寄总重量
                double postWeight;
                // 商品价格
                double productPrice;
                // 运费
                double postPrice; // TODO
                // 订单价格
                double orderPrice;
                // 人民币价格
                double orderCnyPrice;
                productNum = portalCartItems.stream().mapToInt(portalCartItem -> portalCartItem.getQuantity() * portalCartItem.getQuantity()).sum();
                postWeight = portalCartItems.stream().mapToDouble(portalCartItem -> portalCartItem.getWeight().multiply(BigDecimal.valueOf(portalCartItem.getQuantity())).doubleValue()).sum();
                productPrice = portalCartItems.stream().mapToDouble(portalCartItem -> portalCartItem.getPrice().multiply(BigDecimal.valueOf(portalCartItem.getQuantity())).doubleValue()).sum();
                postPrice = 0;
                orderPrice = productPrice + postPrice;
                orderCnyPrice = portalCartItems.stream().mapToDouble(portalCartItem -> portalCartItem.getPrice().divide(BigDecimal.valueOf(rateService.getAuToCnyRate()), 2).doubleValue()).sum();
                PortalDealInfo portalDealInfo = new PortalDealInfo(productNum, postWeight, productPrice, postPrice, orderPrice, orderCnyPrice);
                return new PortalCartItemWithDeal(portalCartItems, portalDealInfo);
            }
        }
        return new PortalCartItemWithDeal();
    }

    private PortalCartItem initPortalCartItem(OmsCartItem item) {
        PmsProduct productInfo = portalProductService.getProductInfo(item.getProductId());
        PortalCartItem portalCartItem = new PortalCartItem();
        portalCartItem.setId(item.getId());
        portalCartItem.setPic(productInfo.getPic());
        portalCartItem.setQuantity(item.getQuantity());
        portalCartItem.setPrice(productInfo.getPrice());
        portalCartItem.setProductId(item.getProductId());
        portalCartItem.setWeight(productInfo.getWeight());
        portalCartItem.setProductName(productInfo.getName());
        portalCartItem.setPublishStatus(productInfo.getPublishStatus());
        portalCartItem.setCnyPrice(productInfo.getPrice().divide(BigDecimal.valueOf(rateService.getAuToCnyRate()), 2));
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
