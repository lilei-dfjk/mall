package com.macro.mall.portal.service;

import com.macro.mall.model.UmsProductCollection;

import java.util.List;

public interface MemberCollectionService {

    int addProduct(UmsProductCollection productCollection);

    int deleteProduct(Long productId);

    List<UmsProductCollection> listProduct();
}
