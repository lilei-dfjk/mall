package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.constans.RedisKey;
import com.macro.mall.portal.service.PortalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PortalProductServiceImpl implements PortalProductService {
    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Cacheable(value = RedisKey.PORTAL_PORTAL, key = "'"+RedisKey.PORTAL_PRODUCT + "'+#productId")
    @Override
    public PmsProduct getProductInfo(long productId) {
        return pmsProductMapper.selectByPrimaryKey(productId);
    }
}
