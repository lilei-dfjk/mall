package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.PmsProductMapper;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.portal.service.PortalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortalProductServiceImpl implements PortalProductService {
    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Override
    public PmsProduct getProductInfo(long productId) {
        return pmsProductMapper.selectByPrimaryKey(productId);
    }
}
