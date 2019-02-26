package com.macro.mall.portal.controller;

import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员登录注册管理Controller
 * Created by macro on 2018/8/3.
 */
@Controller
@Api(tags = "UmsMemberController", description = "会员登录注册管理")
@RequestMapping("/sso")
public class UmsMemberController {
    @Autowired
    private UmsMemberService memberService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @ApiOperation("获取验证码")
    @RequestMapping(value = "/getAuthCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getAuthCode(@RequestParam String telephone) {
        return memberService.generateAuthCode(telephone);
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "login";
    }

    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String telephone,
                           @RequestParam String mail,
                           @RequestParam String authCode) {
        return memberService.register(username, password, telephone, authCode, mail);
    }

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String authCode) {
        String token = memberService.login(username, password);
        if (token == null) {
            return new CommonResult().validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new CommonResult().success(tokenMap);
    }

    @ApiOperation("修改密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(@RequestParam String telephone,
                                 @RequestParam String password,
                                 @RequestParam String authCode) {
        return memberService.updatePassword(telephone, password, authCode);
    }

}
