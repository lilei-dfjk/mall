package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.UmsProductCollectionMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsProductCollection;
import com.macro.mall.model.UmsProductCollectionExample;
import com.macro.mall.portal.service.MemberCollectionService;
import com.macro.mall.portal.service.PortalProductService;
import com.macro.mall.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {

    @Autowired
    private UmsProductCollectionMapper productCollectionMapper;
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private PortalProductService productService;

    @Override
    public int addProduct(UmsProductCollection productCollection) {
        int count = 0;
        UmsMember currentMember = memberService.getCurrentMember();
        PmsProduct productInfo = productService.getProductInfo(productCollection.getProductId());
        productCollection.setCreateTime(new Date());
        productCollection.setMemberId(currentMember.getId());
        productCollection.setProductPic(productInfo.getPic());
        productCollection.setProductPrice(productInfo.getPrice());
        productCollection.setProductName(productInfo.getName());

        UmsProductCollectionExample example = new UmsProductCollectionExample();
        example.createCriteria().andProductIdEqualTo(productCollection.getProductId());
        List<UmsProductCollection> collections = productCollectionMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(collections)) {
            productCollectionMapper.insert(productCollection);
            count = 1;
        }
        return count;
    }

    @Override
    public int deleteProduct(Long id) {
        return productCollectionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsProductCollection> listProduct() {
        UmsMember currentMember = memberService.getCurrentMember();
        UmsProductCollectionExample example = new UmsProductCollectionExample();
        example.createCriteria().andMemberIdEqualTo(currentMember.getId());
        List<UmsProductCollection> collections = productCollectionMapper.selectByExample(example);
        return collections;
    }
}
