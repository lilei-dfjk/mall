package com.macro.mall.portal.service.impl;

import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.ProductComment;
import com.macro.mall.portal.repository.MemberProductCommentRepository;
import com.macro.mall.portal.service.MemberProductCommentService;
import com.macro.mall.portal.service.UmsMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员关注Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class MemberProductCommentServiceImpl implements MemberProductCommentService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private MemberProductCommentRepository memberProductCommentRepository;

    @Override
    public int add(ProductComment productComment) {
        int count = 0;
        UmsMember currentMember = memberService.getCurrentMember();
        ProductComment comment = memberProductCommentRepository.findByProductIdAndUserId(productComment.getProductId(), currentMember.getId());
        if (productComment.getProductId() > 0 && StringUtils.isNotBlank(productComment.getComment())) {
            if (comment == null) {
                productComment.setUserId(currentMember.getId());
                productComment.setUserName(currentMember.getUsername());
                productComment.setUserIcon(currentMember.getIcon());
                productComment.setCreateTime(new Date());
                memberProductCommentRepository.save(productComment);
                return count;
            }
        }
        return -1;
    }

    @Override
    public Page<ProductComment> list(Long productId, int start, int size) {
        Pageable pageable = PageRequest.of(start, size);
        return memberProductCommentRepository.findByProductIdOrderByCreateTimeDesc(productId, pageable);
    }
}
