package com.macro.mall.portal.controller;

import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.model.UserMemberModel;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RequestMapping("/member")
public class UmsMemberInfoController {
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("会员信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public Object info() {
        UserMemberModel model = new UserMemberModel();
        UmsMember currentMember = memberService.getCurrentMember();
        model.setHeadPic(currentMember.getIcon());
        model.setLevel(currentMember.getMemberLevelName());
        model.setNickname(currentMember.getNickname());
        model.setTelephone(currentMember.getPhone());
        model.setMemberId(currentMember.getId());
        model.setUsername(currentMember.getUsername());
        return new CommonResult().success(model);
    }
}
