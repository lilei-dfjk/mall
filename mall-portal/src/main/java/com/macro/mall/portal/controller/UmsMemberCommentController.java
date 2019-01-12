package com.macro.mall.portal.controller;

import com.macro.mall.model.UmsMemberComment;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.service.MemberProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(tags = "UmsMemberCommentController", description = "商品评论")
@RequestMapping("/member/comment")
public class UmsMemberCommentController {
    @Autowired
    private MemberProductCommentService productCommentService;

    @ApiOperation("创建评论")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(long productId, long orderId, String comment, @RequestParam(value = "pics", required = false) String[] pics) {
        UmsMemberComment productComment = new UmsMemberComment();
        productComment.setProductId(productId);
        productComment.setOrderId(orderId);
        if (ArrayUtils.isNotEmpty(pics)) {
            productComment.setPics(StringUtils.join(pics, ","));
        }
        productComment.setComment(comment);
        int count = productCommentService.add(productComment);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("展示评论记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(Long productId, int start, int size) {
        List<UmsMemberComment> memberReadHistoryList = productCommentService.list(productId, start, size);
        return new CommonResult().success(memberReadHistoryList);
    }

}
