package com.macro.mall.portal.service;

import com.macro.mall.constans.PageInfoBean;
import com.macro.mall.model.UmsMemberComment;

import java.util.List;

/**
 * 会员评论Service
 */
public interface MemberProductCommentService {

    int add(UmsMemberComment productComment);

    /**
     * 获取用户评论列表
     */
    PageInfoBean<UmsMemberComment> list(Long productId, int start, int size);
}
