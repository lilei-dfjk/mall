package com.macro.mall.portal.controller;

import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.domain.ProductComment;
import com.macro.mall.portal.service.MemberProductCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 会员登录注册管理Controller
 * Created by macro on 2018/8/3.
 */
@Controller
@Api(tags = "UmsMemberCommentController", description = "商品评论")
@RequestMapping("/member/comment")
public class UmsMemberCommentController {
    @Autowired
    private MemberProductCommentService productCommentService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("创建评论")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@RequestBody ProductComment productComment) {
        int count = productCommentService.add(productComment);
        if (count > 0) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("展示评论记录")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(Long productId, int start, int size) {
        Page<ProductComment> memberReadHistoryList = productCommentService.list(productId, start, size);
        return new CommonResult().success(memberReadHistoryList);
    }

}
