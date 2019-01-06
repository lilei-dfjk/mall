package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.ProductComment;
import org.springframework.data.domain.Page;

/**
 * 会员评论Service
 */
public interface MemberProductCommentService {

    int add(ProductComment productComment);

    /**
     * 获取用户评论列表
     */
    Page<ProductComment> list(Long productId, int start, int size);
}
