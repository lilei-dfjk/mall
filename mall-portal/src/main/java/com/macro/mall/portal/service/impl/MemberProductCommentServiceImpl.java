package com.macro.mall.portal.service.impl;

import com.macro.mall.mapper.UmsMemberCommentMapper;
import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsMemberComment;
import com.macro.mall.model.UmsMemberCommentExample;
import com.macro.mall.portal.service.MemberProductCommentService;
import com.macro.mall.portal.service.UmsMemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 会员关注Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class MemberProductCommentServiceImpl implements MemberProductCommentService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private UmsMemberCommentMapper memberCommentMapper;

    @Override
    public int add(UmsMemberComment productComment) {
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMemberCommentExample example = new UmsMemberCommentExample();
        example.createCriteria().andMemberIdEqualTo(currentMember.getId()).andProductIdEqualTo(productComment.getProductId());
        List<UmsMemberComment> umsMemberComments = memberCommentMapper.selectByExample(example);
        if (productComment.getProductId() > 0 && StringUtils.isNotBlank(productComment.getComment())) {
            if (CollectionUtils.isEmpty(umsMemberComments)) {
                productComment.setMemberId(currentMember.getId());
                productComment.setCreateTime(new Date());
                memberCommentMapper.insert(productComment);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public List<UmsMemberComment> list(Long productId, int start, int size) {
        UmsMemberCommentExample example = new UmsMemberCommentExample();
        example.createCriteria().andProductIdEqualTo(productId);
        example.setOrderByClause("'create_time' DESC");
        return memberCommentMapper.selectByExample(example);
    }
}
