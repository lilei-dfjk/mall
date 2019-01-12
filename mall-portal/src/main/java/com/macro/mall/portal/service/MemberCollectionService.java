package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.MemberProductCollection;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 会员收藏Service
 * Created by macro on 2018/8/2.
 */
public interface MemberCollectionService {
    int addProduct(MemberProductCollection productCollection);

    int deleteProduct(Long productId);

    Page<MemberProductCollection> listProduct(int start, int size);
}
