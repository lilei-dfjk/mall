package com.macro.mall.portal.service.impl;

import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.MemberProductCollection;
import com.macro.mall.portal.repository.MemberProductCollectionRepository;
import com.macro.mall.portal.service.MemberCollectionService;
import com.macro.mall.portal.service.PortalProductService;
import com.macro.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 会员收藏Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {
    @Autowired
    private MemberProductCollectionRepository productCollectionRepository;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PortalProductService productService;

    @Override
    public int addProduct(MemberProductCollection productCollection) {
        int count = 0;
        UmsMember currentMember = memberService.getCurrentMember();
        PmsProduct productInfo = productService.getProductInfo(productCollection.getProductId());
        productCollection.setCreateTime(new Date());
        productCollection.setMemberId(currentMember.getId());
        productCollection.setProductPic(productInfo.getPic());
        productCollection.setProductPrice(productInfo.getPrice().doubleValue());
        productCollection.setProductName(productInfo.getName());
        MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(productCollection.getMemberId(), productCollection.getProductId());
        if (findCollection == null) {
            productCollectionRepository.save(productCollection);
            count = 1;
        }
        return count;
    }

    @Override
    public int deleteProduct(Long productId) {
        UmsMember currentMember = memberService.getCurrentMember();
        return productCollectionRepository.deleteByMemberIdAndProductId(currentMember.getId(), productId);
    }

    @Override
    public Page<MemberProductCollection> listProduct(int start, int size) {
        UmsMember currentMember = memberService.getCurrentMember();
        Pageable pageable = PageRequest.of(start, size);
        return productCollectionRepository.findByMemberIdOrderByCreateTimeDesc(currentMember.getId(), pageable);
    }
}
