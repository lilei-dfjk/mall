package com.macro.mall.portal.component;

import com.macro.mall.portal.util.JwtTokenUtil;
import com.macro.mall.portal.util.RedisTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTokenUtil redisTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        redisTokenUtil.setTokenRefresh(token, userDetails.getUsername(), userDetails);
        token = tokenHead + token;
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setHeader(tokenHeader, token);
        response.getWriter().print("{\"code\":200,\"message\":\"登录成功\"}");
         response.getWriter().flush();
    }
}
