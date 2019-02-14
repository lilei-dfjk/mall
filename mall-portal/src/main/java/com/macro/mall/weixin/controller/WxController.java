package com.macro.mall.weixin.controller;

import com.macro.mall.weixin.AesException;
import com.macro.mall.weixin.util.AuthUtil;
import com.macro.mall.weixin.util.WXPublicUtils;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@Controller
@Api(tags = "WxVerifyController", description = "会员收藏管理")
@RequestMapping("/wx")
public class WxController {
    @RequestMapping("/verify")
    @ResponseBody
    public String verifyWXToken(HttpServletRequest request) throws AesException {
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (WXPublicUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
            return echostr;
        }
        return null;
    }
    @RequestMapping("/auth")
    @ResponseBody
    public String auth(HttpServletRequest request) throws AesException {
        String backUrl = "http://brucebsc.eicp.net/WxAuth/wxCallBack";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ AuthUtil.APPID
                + "&redirect_uri="+ URLEncoder.encode(backUrl)
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
//        resp.sendRedirect(url);
        return null;
    }
    @RequestMapping("/auth/callback")
    @ResponseBody
    public String authCallback(HttpServletRequest request) throws AesException {
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (WXPublicUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
            return echostr;
        }
        return null;
    }

}
